package ShiftyAlpaca.service;

import ShiftyAlpaca.model.*;
import ShiftyAlpaca.repository.ExplainResultRepo;
import ShiftyAlpaca.repository.ResultRowMapper;
import ShiftyAlpaca.repository.SlackWrapperRepo;
import ShiftyAlpaca.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** The EventWrapperService (Singleton by virtue of @Service) is a
 * bean stood up by the framework. This is where the business
 * logic resides, decoupled from the respective repository. The methods
 * here receive requests, translate/run the EXPLAIN statements, persist
 * the data via the UserRepository, and respond to the user with a recommendation.
 *
 */
@Service  //This makes the service a singleton by default
public class SlackEventService {

  @Autowired
  private SlackWrapperRepo slackWrapperRepo;
  @Autowired
  private ExplainResultRepo explainResultRepo;
  @Autowired
  private UserRepo userRepo;

  //Intermediate members. Remove after local DB testing is done and
  //  messaging system is set up
  //  application.properties file maintains the values for these variable
  //  app.prop is not version-controlled, in order to protect those vars
  @Value("${spring.datasource.alpha.username}")
  private String testDBusername;
  @Value("${spring.datasource.alpha.password}")
  private String testDBpassword;
  @Value("${spring.datasource.alpha.url}")
  private String testDBURL;
  @Value("${spring.datasource.alpha.driver-class-name}")
  private String testDBdriver;

  private String URL_BASE = "https://slack.com/api";

  private CredentialService credentialService;

  public SlackEventService(CredentialService credentialService) { this.credentialService = credentialService; }

  /**Single method that will store a user query in a
   * database for record-keeping. Method will then run EXPLAIN on
   * selected DB, store that result, then "respond" to user
   * via postToUser().
   *
   * ASYNC: this method runs during/after AnalyzerController responds
   * to Slack API with HTTP 200 OK. Relies on Executor.class @Bean
   * inside of Application.class file.
   *
   * @param event - Text string from user via Slack (Json)
   *
   */
  @Async
  public void respond(JsonNode event) throws InterruptedException {
    if (!messageFromBot(event)) {
      ObjectMapper mapper = new ObjectMapper();
      try {
        //Create relevant models for persistence. User is the parent record; we map the persistence entry and save
        //  using the userRepo later in method.
        User slackUser = new User(new UserIdentity(event.get("team_id").asText(), event.get("event").get("user").asText()));
        List<SlackWrapper> queries = new ArrayList<>();
        SlackWrapper slackWrapper = mapper.readValue(event.toString(), SlackWrapper.class);
        SlackEvent slackEvent = mapper.readValue(event.get("event").toString(), SlackEvent.class);

        //Connect the models prior to persisting them
        slackWrapper.setUser(slackUser);
        slackWrapper.setEvent(slackEvent);
        slackEvent.setSlackWrapper(slackWrapper);

        //Add the newest query (or slackwrapper) and update the User's list of prior queries
        queries.add(slackWrapper);
        slackUser.setQueries(queries);

        //connect to test target DB (this is what EXPLAIN statement will target)
        //    UserRepo is responsible for connecting to and updating the 'test' query logging DB
        DataSource ds = DataSourceBuilder.create()
                .username(testDBusername)
                .password(testDBpassword)
                .url(testDBURL)
                .driverClassName(testDBdriver)
                .build();

        //translate slackEvent text into EXPLAIN
        //  Pair<String, ...> - is the query with args removed to prevent SQL injection
        //  Pair<..., List<Object> - is the list of args extracted from the SQL statement
        Pair<String, List<Object>> parsedQueryTuple =
                parseSqlForPreparedStatement(slackWrapper.getEvent().getText());

        //run jdbcTemplate statement; using the above tuple/pair
        JdbcTemplate explainThis = new JdbcTemplate(ds);
        List<ExplainResult> explainQueryResults = runExplainQuery(explainThis, parsedQueryTuple);

        //According to query log DB schema, ExplainResults need a mapping to the parent query (slackWrapper)
        //  And that parent wrapper maintains a List of ExplainResults. Create the bi-directional rel here.
        //List<ExplainResult> results = new ArrayList<>();
        for (ExplainResult r : explainQueryResults) {
          r.setSlackWrapper(slackWrapper);
        }
        slackWrapper.setExplain_results(explainQueryResults);

        //All bi-directional rels among the relevant tables are created; persist it via the UserRepository object
        userRepo.save(slackUser);

        //run decision tree logic
        String response = courseOfAction(explainQueryResults);
        //send 'Proceed' or 'Caution' to user
        postToUser(slackWrapper.getEvent().getChannel(),
                   response,
                  URL_BASE + "/chat.postMessage");

      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
  }

  /** Parse a user submitted SQL statement for parameters, e.g. the argument for a
   * WHERE clause. Pull out these arguments, place them in a List<Object> and replace
   * each argument in the original string with a question mark (JDBC PreparedStatement
   * syntax). Return this in a tuple so that a JDBC PreparedStatement can be run against
   * the requested DB.
   *
   * @param   -SQL string statement entered by app user
   * @return  -Tuple of SQL statement with arguments extracted for use in JDBC PreparedStatement
   */
  private Pair<String, List<Object>> parseSqlForPreparedStatement(String sql){
    List<Object> retList = new ArrayList<>();

    //Find any instances of single/double quoted data in the SQL statement
    Pattern pattern = Pattern.compile("‘(.*?)’", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(sql);
    //Find each instance of above pattern and add to list of args to return to user
    while (matcher.find()) {
      retList.add(matcher.group(1));
    }
    //Replace found patterns with '?' and return alongside extracted args
    matcher.reset();
    return new Pair<>(matcher.replaceAll("?"), retList);
  }

  /** Create a list of ExplainResult objects after running the
   * query on the chosen DB in context.
   *
   * @param ds - The connection to the target DB
   * @param sql - Tuple<String,List> of the SQL statement along with parameterized elements
   *
   * @return - List<ExplainResult> as some EXPLAIN statements return multiple rows
   */
  public List<ExplainResult> runExplainQuery(JdbcTemplate ds, Pair<String, List<Object>> sql) {
    try{
      String parsedSQL = sql.getValue0();
      Object[] args = sql.getValue1().toArray();
      List<ExplainResult> ret = ds.query("EXPLAIN " + parsedSQL,
              args, new ResultRowMapper());
      return ret;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    ExplainResult ret = new ExplainResult();
    List<ExplainResult> retList = new ArrayList<>();
    retList.add(ret);
    return retList;
  }

  /** When the applications provides a response for the Slackbot to post in chat, that message post
   * by the bot will also be picked up by the Slack Events API and will itself be POSTed back to the application.
   *
   * This potential loop is defeated with this method, which simply checks for the existence of a bot_id
   * in the SlackEvent post. The field does not exist in user message events.
   *
   * @return - true if message slackEvent is from a Slackbot.
   */
  private boolean messageFromBot(JsonNode event) {
    return (event.get("event").get("bot_id") != null);
  }

  /** This method contains the primary logic that determines whether the SQL that the front end user
   * is acceptable for production use or whether they should visit a DBA to refine their query.
   *
   * @param explainQueryResults - set of result rows returned after the user's SQL was run in EXPLAIN
   * @return  String - basically a yay or nay for now, but could add character later, possibly with ENUM of responses
   */
  private String courseOfAction(List<ExplainResult> explainQueryResults){
    //TODO: flesh out this query to handle more complex combinations of EXPLAIN results and to also take
    //TODO:     into account the field variables of each EXPLAIN result row. (one row per table accessed by EXPLAIN)
    /**
     * Questions:
     * does it have index?
     *    are possible rows above 100K?
     *      deny query
     *    else, go to next step
     *
     */

    //If EXPLAIN result 'type' is not in this set, warn user to consult DBA
    Set<String> allowedTypes = new HashSet<>(Arrays.asList("eq_ref",
                                      "fulltext",
                                      "index_merge",
                                      "index_sub",
                                      "index",
                                      "range"));
    //If EXPLAIN result 'extras' contains verbiage in this list, warn user to consult DBA
    Set<String> warnableExtras = new HashSet<>(Arrays.asList("Distinct",
                                                            "Full scan on NULL key",
                                                            "Impossible HAVING",
                                                            "Impossible WHERE noticed after reading const tables",
                                                            "Impossible WHERE",
                                                            "No matching min/max row",
                                                            "no matching row in const table",
                                                            "No tables used",
                                                            "unique row not found",
                                                            "Using filesort"));
    //Only warn user to consult DBA on lack of index usage if row count is over 100k
    for (ExplainResult r : explainQueryResults){
      if (r.getQueried_rows() > 100000){
        if (r.getQueried_key() == "NULL"){
          return "Recommend you consult with a friendly DBA.";
        }
      }
      if (!allowedTypes.contains(r.getType())){
          return "Recommend you consult with a friendly DBA.";
      }
      if (warnableExtras.contains(r.getExtra())){
          return "Recommend you consult with a friendly DBA.";
      }
    }
    return "No apparent conflicts. Thanks for visiting!";
  }

  /** This method will be called from the Async respond() above in order to post a message
   * back to the Slack user.
   *
   * @param channel - can be the UID for a room or a DM with a user
   * @param text - the response we wish to send to the Slack user requesting our service
   */
  public void postToUser(String channel, String text, String postURL){
    System.out.println("Testing PostToUser: " + credentialService.getCredentials().getAccess_token());

    RestTemplate resp = new RestTemplate();
    //MultiValueMap might come in handy with later functionality
    MultiValueMap<String, Object> map = new LinkedMultiValueMap();
    map.add("channel", channel);
    map.add("text", text);
    map.add("token", credentialService.getCredentials().getAccess_token());

    //RestTemplate does POST on URL with map object of response data
    resp.postForObject(postURL, map, String.class);
  }

  /** Handles the Slack API's verification slackEvent when initially establishing the url
   * of this application as the true home of the bot's brains.
   *
   * @param challenge comes in at the controller from Slack. No need to store, just return.
   * @return - This will be the same token sent by the Slack API in the 'challenge' field.
   */
  public VerificationResponse verificationResponse(JsonNode challenge) {
    return new VerificationResponse(challenge);
  }

  /** Only used for JUnit testing. The autowiring does not work with JUnit tests,
   * but the repos are necessary in order to test the processes.
   *
   * @param swr
   * @param err
   */
  public void setReposForTesting(SlackWrapperRepo swr, ExplainResultRepo err) {
    this.slackWrapperRepo = swr;
    this.explainResultRepo = err;
  }
}

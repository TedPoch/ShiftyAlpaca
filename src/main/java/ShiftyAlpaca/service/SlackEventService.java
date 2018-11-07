package ShiftyAlpaca.service;

import ShiftyAlpaca.model.*;
import ShiftyAlpaca.repository.ExplainResultRepo;
import ShiftyAlpaca.repository.SlackWrapperRepo;
import ShiftyAlpaca.repository.ResultRowMapper;
import ShiftyAlpaca.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** The EventWrapperService (Singleton by virtue of @Service) is
 * stood up by the AnalyzerController. This is where the business
 * logic resides, decoupled from the respective repository. The
 * repo will database the query request, translate/run the EXPLAIN
 * query, and store the result for retrieval by this service class.
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
  @Value("${spring.datasource.alpha.username}")
  private String testDBusername;
  @Value("${spring.datasource.alpha.password}")
  private String testDBpassword;
  @Value("${spring.datasource.beta.url}")
  private String testDBURL;
  @Value("${spring.datasource.beta.driver-class-name}")
  private String testDBdriver;

  //Need these to post responses to Slack until OAUTH code is ready
  private String URL_BASE = "https://slack.com/api";
  @Value( "${client.id}" )
  private String CLIENT_ID;
  @Value( "${client.secret}" )
  private String CLIENT_SECRET;
  @Value("${bot.token}")
  private String BOT_TOKEN;

  /**TODO: Temporary: Single method that will store a user query in a
   * database for record-keeping. Method will then run EXPLAIN on
   * selected other DB, store that result, then "respond" to user
   * via the AnalyzeController with a recommendation to speak with
   * a DBA.
   *
   * @param event - Text string from user via Slack (Json)
   *
   * @return - Text string with recommended course of action.
   */
  public void respond(JsonNode event) {
    if (!messageFromBot(event)) {
      ObjectMapper mapper = new ObjectMapper();
      try {
        //Creating the slackWrapper with the ObjMapper also creates the nested
        //  'SlackEvent' object
        //  BUT, we need to set the foreign key of the SlackEvent to the parent wrapper

//      Instantiate/Load parent object
//      Instantiate/Load child objects
//      Set the parent object in the child objects
//      Create a collection of child objects
//      Set the collection of child objects on the parent
//      Save the parent

        User slackUser = new User(new UserIdentity(event.get("team_id").asText(), event.get("event").get("user").asText()));
        List<SlackWrapper> queries = new ArrayList<>();

        SlackWrapper slackWrapper = new SlackWrapper();
        slackWrapper = mapper.readValue(event.toString(), SlackWrapper.class);
        slackWrapper.setUserIdentity(slackUser.getUserIdent());

        SlackEvent slackEvent = new SlackEvent();
        slackEvent = mapper.readValue(event.get("event").toString(), SlackEvent.class);

        slackWrapper.setEvent(slackEvent);
        slackEvent.setSlackWrapper(slackWrapper);

        queries.add(slackWrapper);
        slackUser.setQueries(queries);
        userRepo.save(slackUser);

        //translate slackEvent text into EXPLAIN
        Pair<String, List<Object>> parsedQueryTuple =
                parseSqlForPreparedStatement(slackWrapper.getEvent().getText());

        //QueryDatabaseContextHolder.set(QueryDatabase.TEST_ALPHA);

        //connect to DB
        //QueryDatabaseContextHolder.set(QueryDatabase.TEST_BETA);
        DataSource ds = DataSourceBuilder.create()
                .username(testDBusername)
                .password(testDBpassword)
                .url(testDBURL)
                .driverClassName(testDBdriver)
                .build();

        //run jdbcTemplate statement
        JdbcTemplate explainThis = new JdbcTemplate(ds);
        List<ExplainResult> result = analyzeQuery(explainThis, parsedQueryTuple);

        //store result app's record-keeping database
        //  established by ExplainResultRepo
        for (ExplainResult r : result) {
          explainResultRepo.save(r);
        }

        //run decision tree logic

        //return recommendation string to user

        postToUser(slackWrapper.getEvent().getChannel(),
                slackWrapper.getEvent().getText(),
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

  /** Create a MariaDB ExplainResult statement object after running the
   * query on the chosen DB in context.
   * @return
   */
  public List<ExplainResult> analyzeQuery(JdbcTemplate ds, Pair<String, List<Object>> sql) {
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

  /** When the applications provides a response for the Slackbot to post in chat, that chatpost
   * will also be picked up by the Slack Events API and will itself be POSTed back to the application.
   *
   * This potential loop is defeated with this method, which simply checks for the existence of a bot_id
   * in the SlackEvent post. The field does not exist in user message events.
   *
   * @return - true if message slackEvent is from a Slackbot.
   */
  private boolean messageFromBot(JsonNode event) {
    return (event.get("event").get("bot_id") != null);
  }

  /** This method will be called from the Async respond() above in order to post a message
   * back to the Slack user.
   *
   * @param channel - can be the UID for a room or a DM with a user
   * @param text - the response we wish to send to the Slack user requesting our service
   */
  public void postToUser(String channel, String text, String postURL){
    RestTemplate resp = new RestTemplate();
    MultiValueMap<String, Object> map = new LinkedMultiValueMap();
    map.add("channel", channel);
    map.add("text", text);
    map.add("token", BOT_TOKEN); //TODO: work on saving the token somewhere secure

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

  public synchronized boolean addEvent(SlackWrapper event) {
    //TODO: stub for slackEvent service method
    return false;
  }

  public SlackWrapper getEventByEventID(String event_id) {
    //TODO: stub for slackEvent service method
    return null;
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

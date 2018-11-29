package ShiftyAlpaca.service;

import ShiftyAlpaca.model.Credentials;
import ShiftyAlpaca.model.SlackEvent;
import ShiftyAlpaca.model.SlackWrapper;
import ShiftyAlpaca.model.User;
import ShiftyAlpaca.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.management.Query;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppService {

  private CredentialService credentialService;
  private UserService userService;
  private QueryService queryService;
  private UserRepo userRepo;

  public AppService(CredentialService credentialService,
                    UserService userService,
                    QueryService queryService,
                    UserRepo userRepo) {
    this.credentialService = credentialService;
    this.userService = userService;
    this.queryService = queryService;
    this.userRepo = userRepo;
  }

  /** See https://api.slack.com/docs/oauth
   *
   * After the Slack user clicks the install button for the app and approves the install,
   * the Slack API sends back a code that the application then uses to acquire an OAUTH
   * token for all subsequent Events API interactions.
   *
   * CLIENT_ID and CLIENT_SECRET should be set as environment variables inside of the application's
   * Docker container. The two vars are created when the Slack workspace user creates their own
   * version of the application and requisite bot user on the Slack API website.
   *
   * @param code - temporary code sent by Slack and used in this method to exchange for a longer term
   *             bot OAUTH token
   */
  public void authorize(String code) throws IOException {
    //Slack wants a POST, but can't accept JSON (will return JSON though)
      //build HttpEntity with headers/map
    //build a map with the vars to send back to Slack OAUTH
    //create resttemplate and responseentity objects
    //add vars and postForEntity() - getForEntity() works as well, but Slack recommends a POST
    //create authorizationInfo object that holds the bot token for the current installation

    //Set POST language
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    //body of request - NULL for now, but included for later possible expansion
    //    ***DO NOT PUT*** client_id and client_secret in here (RFC 6749 says let HTTP auth handle)
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    //This map will be expanded into the first param of postForEntity()
    Map<String, String> authVars = new HashMap<>();
    authVars.put("client_id", System.getenv("CLIENT_ID"));
    authVars.put("client_secret", System.getenv("CLIENT_SECRET"));
    authVars.put("code", code);

    //Used in postForEntity, just to set header and satisfy method param reqs
    HttpEntity<String> request = new HttpEntity<>(headers);

    RestTemplate bottle = new RestTemplate();
    ResponseEntity<String> message = bottle.postForEntity("https://slack.com/api/oauth.access?" +
                    "client_id={client_id}&client_secret={client_secret}&code={code}",
                    request,
                    String.class,
                    authVars);

    //Pull the bot OAUTH token out of the JSON string returned from Slack
    ObjectMapper mapper = new ObjectMapper();
    JsonNode main = mapper.readTree(message.getBody());
    JsonNode bot = main.path("bot");

    System.out.println("Auth Method: " + bot.get("bot_access_token").asText());

    credentialService.setCredentials(new Credentials(bot.get("bot_user_id").asText(),
                                                      bot.get("bot_access_token").asText()));
  }

  public void handleUserRequest(JsonNode json){
    queryService.validate(json);
    User user = userService.findOrCreateUser(json);

    SlackWrapper wrapper = queryService.createWrapper(json);
    SlackEvent event = queryService.createWrappedEvent(json);

    //Connect the models prior to persisting them
    wrapper.setUser(user);
    wrapper.setEvent(event);
    event.setSlackWrapper(wrapper);

    user.setQuery();
    user.setResults();

    userRepo.save(user);

  }
}

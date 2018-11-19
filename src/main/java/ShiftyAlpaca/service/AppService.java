package ShiftyAlpaca.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppService {

  //TODO: handle the OAUTH

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
  public void authorize(String code){
    //build a map with the vars to send back to Slack OAUTH
    //create resttemplate and responseentity objects
    //add vars and getForEntity()
    //create authorizationInfo object that holds the bot token for the current installation

    Map<String, String> authVars = new HashMap<>();
    authVars.put("client_id", System.getenv("CLIENT_ID"));
    authVars.put("client_secret", System.getenv("CLIENT_SECRET"));
    authVars.put("code", code);

    RestTemplate bottle = new RestTemplate();
    //ResponseEntity<String> message = bottle.postForEntity()

  }
}

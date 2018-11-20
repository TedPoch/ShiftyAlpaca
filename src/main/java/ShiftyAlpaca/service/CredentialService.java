package ShiftyAlpaca.service;

import ShiftyAlpaca.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/** Just a service to store the Slack Credentials.class object (OAUTH token) for
 * the application for its current install into the team's workspace. This should
 * renew each time the app is started and/or re-installed to the team workspace.
 *
 */
@Service
public class CredentialService {

  private final Map<String, Object> map = new HashMap<>();

  public void setCredentials(Credentials botIDandToken){
    map.put("bot_creds", botIDandToken);
  }

  public Credentials getCredentials(){
    return (Credentials) map.get("bot_creds");
  }
}

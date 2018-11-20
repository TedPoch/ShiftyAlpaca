package ShiftyAlpaca.model;

/** Just an object to store the Slack credentials (OAUTH token) for the application
 * for its current install into the team's workspace. This should renew each time the
 * app is started and/or re-installed to the team workspace.
 *
 */
public class Credentials {

  private String id;
  private String access_token;

  public Credentials(String id, String token){
    this.id = id;
    this.access_token = token;
  }

  public String getId() {
    return id;
  }

  public String getAccess_token() {
    return access_token;
  }
}

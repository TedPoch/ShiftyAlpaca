package ShiftyAlpaca.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/** This class establishes the composite primary key for each
 * instance/persistence of a new User object/DB row. It is referred
 * in the User.class by the annotation @EmbeddedID.
 *
 * According to Slack API documentation, a 'user' identifier starts
 * with either a 'W' or a 'U' and is only unique to the user's current
 * workspace. So the combination of a 'user' and 'team_id' is necessary
 * to guarantee a unique User in this application.
 *
 * The User object constructor requires an instantiation of this class.
 * The User.class does not need to be serialized.
 *
 */
@Embeddable
public class UserIdentity implements Serializable {

  @NotNull
  @Size(max = 12)
  private String team_id;

  @NotNull
  @Size(max = 12)
  private String user;

  public UserIdentity(){}

  public UserIdentity(String team_id, String user){
    this.team_id = team_id;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserIdentity that = (UserIdentity) o;

    //Return false if either the team_id or user is not the same
    if (!team_id.equals(that.team_id)) return false;
    return user.equals(that.user);
  }

  @Override
  public int hashCode() {
    int result = team_id.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }

  public String getTeam_id() {
    return team_id;
  }

  public void setTeam_id(String team_id) {
    this.team_id = team_id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}

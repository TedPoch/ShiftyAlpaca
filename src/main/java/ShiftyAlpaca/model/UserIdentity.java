package ShiftyAlpaca.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class UserIdentity {

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

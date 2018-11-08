package ShiftyAlpaca.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.List;

/** Contains User (customer) information other than PK elements
 * found in the UserIdent composite PK class. The "Id" for this
 * class is not a second PK or table index. The @EmbeddedID simply
 * uses a related UserIdentity object as the index.
 *
 * According to Slack API documentation, a 'user' identifier starts
 * with either a 'W' or a 'U' and is only unique to the user's current
 * workspace. So the combination of a 'user' and 'team_id' is necessary
 * to guarantee a unique User in this application.
 *
 */
@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userIdent")
public class User {

  @EmbeddedId
  private UserIdentity userIdent;
  private String name;    //TODO: System-wide user name???
  private String role;    //TODO: DBA vs. engineer???


  /** This List holds those past and present queries (SlackWrapper
   * objects) made by a particular user on a particular team.
   *
   * See @ManyToOne annotation on User member in SlackWrapper.class
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<SlackWrapper> queries;

  public User() {}

  public User(UserIdentity userIdent) {
    this.userIdent = userIdent;
  }

  public UserIdentity getUserIdent() {
    return userIdent;
  }

  public void setUserIdent(UserIdentity userIdent) {
    this.userIdent = userIdent;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  public List<SlackWrapper> getQueries() {
    return queries;
  }

  public void setQueries(List<SlackWrapper> queries) {
    this.queries = queries;
  }
}

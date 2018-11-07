package ShiftyAlpaca.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "user_id")
public class User {
//  @Id
//  @GeneratedValue(strategy= GenerationType.AUTO)
//  private Integer user_id;

  @EmbeddedId
  private UserIdentity userIdent;

  private String name;    //TODO: System-wide user name???
  private String role;    //TODO: DBA vs. engineer???

//  private String team_id;
//  private String user;

  @OneToMany(cascade = CascadeType.ALL,
          fetch = FetchType.LAZY,
          mappedBy = "user")
  private List<SlackWrapper> queries = new ArrayList<>();

  public User() {}

//  public User(String team_id, String user) {
//    this.team_id = team_id;
//    this.user = user;
//  }

  public User(UserIdentity userIdent) {
    this.userIdent = userIdent;
  }
}

package ShiftyAlpaca.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;

  private String name;    //TODO: System-wide user name???
  private String role;    //TODO: DBA vs. engineer???
}

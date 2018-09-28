package tedpoch.ShiftyAlpaca.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(generator = "system-uuid") //PK should be random as a security measure
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String id;

  private String name;    //TODO: System-wide user name???
  private String role;    //TODO: DBA vs. engineer???
}

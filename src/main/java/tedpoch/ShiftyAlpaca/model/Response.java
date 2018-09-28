package tedpoch.ShiftyAlpaca.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/* This interface allows us to create a return type in the
    AnalyzerController.java returnResult() method. I can now
    implement the interface in my VerificationResponse and
    QueryResponse classes and return either of them in
    the controller method's switch statement.
 */
public class Response {
  @Id
  @GeneratedValue(generator = "system-uuid") //PK should be random as a security measure
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  String app_id;
}

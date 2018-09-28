package tedpoch.ShiftyAlpaca.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/* This class will serve as the basis for the ultimate response
    that the application will form on behalf of the user when they
    submit a DB query for analysis.
 */
public class QueryResponse extends Response {
  private String id;
  private String response;

  public QueryResponse(String response) {
    super();
    this.response = response;
  }
}

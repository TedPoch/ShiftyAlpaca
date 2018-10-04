package ShiftyAlpaca.model;

import lombok.Data;

/* This class will serve as the basis for the ultimate response
    that the application will form on behalf of the user when they
    submit a DB query for analysis.
 */
@Data
public class QueryResponse extends Response {

  private String response;

  public QueryResponse() {
    super();
  }
}

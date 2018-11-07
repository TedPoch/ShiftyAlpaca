package ShiftyAlpaca.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.persistence.*;

/* This class serves as a JSON object model that will be transmitted back
    to the Slack API when it performs the application verification handshake.
    Slack sends the challenge token and this app's controller responds with that
    same token.

 */
@Data
//@Entity
//@Table(name = "verification_challenges")
public class VerificationResponse extends Response {

  private String token;
  private String challenge;
  private String type;

  public VerificationResponse() {}

  public VerificationResponse(JsonNode event) {
    this.token = event.get("token").asText();
    this.challenge = event.get("challenge").asText();
    this.type = event.get("type").asText();
  }
}

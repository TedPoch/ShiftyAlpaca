package ShiftyAlpaca.model;

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

  private String challenge;

  public VerificationResponse(String challenge) {
    this.challenge = challenge;
  }
}

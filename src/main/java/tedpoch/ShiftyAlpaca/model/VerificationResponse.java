package tedpoch.ShiftyAlpaca.model;

import lombok.Data;

/* This class serves as a JSON object model that will be transmitted back
    to the Slack API when it performs the application verification handshake.
    Slack sends the challenge token and this app's controller responds with that
    same token.

 */

public class VerificationResponse extends Response {
  private String challenge;

  public VerificationResponse(String challenge) {
    this.challenge = challenge;
  }
}
//be cautious of incidental injection
//run DB locally and try stuff out
//start reading about EXPLAIN analysis
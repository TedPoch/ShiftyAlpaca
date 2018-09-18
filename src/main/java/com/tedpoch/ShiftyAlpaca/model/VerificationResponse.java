package com.tedpoch.ShiftyAlpaca.model;

/* This class serves as a JSON object model that will be transmitted back
    to the Slack API when it performs the application verification handshake.
    Slack sends the challenge token and this app's controller responds with that
    same token.

 */
public class VerificationResponse implements EventResponse {

    private String challenge;

    public VerificationResponse(String challenge) {
        this.challenge = challenge;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

}

package com.tedpoch.ShiftyAlpaca.model;

public class VerificationResponse {

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

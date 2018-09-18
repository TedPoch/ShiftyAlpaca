package com.tedpoch.ShiftyAlpaca.model;

/* This class will serve as the basis for the ultimate response
    that the application will form on behalf of the user when they
    submit a DB query for analysis.

 */
public class QueryResponse implements EventResponse {

    private String text;

    public QueryResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

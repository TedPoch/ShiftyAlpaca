package com.tedpoch.ShiftyAlpaca.model;

/*  This class represents the JSON object for a specific Slack Events API
    callback and is always wrapped inside of an EventWrapper when sent from
    Slack and when saved by this application.

 */
public class Event {
    private String type;
    private String channel;
    private String user;
    private String text;
    private String ts;
    private String event_ts;
    private String channel_type;

    public String getType() {
        return type;
    }

    public String getChannel() {
        return channel;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getTs() {
        return ts;
    }

    public String getEvent_ts() {
        return event_ts;
    }

    public String getChannel_type() {
        return channel_type;
    }
}

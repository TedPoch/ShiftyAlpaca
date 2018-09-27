package com.tedpoch.ShiftyAlpaca.model;

import lombok.Data;

/*  This class represents the JSON object for a specific Slack Events API
    callback and is always wrapped inside of an EventWrapper when sent from
    Slack and when saved by this application.

 */
@Data
public class Event {
    private String type;
    private String channel;
    private String user;
    private String text;
    private String ts;
    private String event_ts;
    private String channel_type;
}

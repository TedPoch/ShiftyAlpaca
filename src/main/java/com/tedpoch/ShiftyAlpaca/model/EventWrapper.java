package com.tedpoch.ShiftyAlpaca.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

/* This EventWrapper class is a representation of the basic JSON object
    that the Slack API will send regardless of which of the Event API events
    occurs. The Event member is a nested JSON object and contains info specific
    to the relevant Slack Events API callback.

 */
@Data
public class EventWrapper {
    private String token;
    private String team_id;
    private String api_app_id;
    private Event event;
    private String type;
    private List<String> authed_teams;
    private String event_id;
    private String event_time;
}

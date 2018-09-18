package com.tedpoch.ShiftyAlpaca.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/* This EventWrapper class is a representation of the basic JSON object
    that the Slack API will send regardless of which of the Event API events
    occurs. The Event member is a nested JSON object and contains info specific
    to the relevant Slack Events API callback.

 */
public class EventWrapper {
    private String token;
    private String team_id;
    private String api_app_id;
    private Event event;
    private String type;
    private List<String> authed_teams;
    private String event_id;
    private String event_time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getApi_app_id() {
        return api_app_id;
    }

    public void setApi_app_id(String api_app_id) {
        this.api_app_id = api_app_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAuthed_teams() {
        return authed_teams;
    }

    public void setAuthed_teams(List<String> authed_teams) {
        this.authed_teams = authed_teams;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

}

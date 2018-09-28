package tedpoch.ShiftyAlpaca.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/* This EventWrapper class is a representation of the basic JSON object
    that the Slack API will send regardless of which of the Event API events
    occurs. The Event member is a nested JSON object and contains info specific
    to the relevant Slack Events API callback. (See tedpoch.ShiftyAlpaca.Event.java)
 */
@Data
@Entity
@Table(name = "event_wrappers")
public class EventWrapper {
  private String token;
  private String team_id;
  private String api_app_id;
  private Event event;
  private String type;

  @Transient
  private List<String> authed_teams;

  @Id     //event_id arrives with wrapper and is globally unique across workspaces
  private String event_id;

  private String event_time;
}

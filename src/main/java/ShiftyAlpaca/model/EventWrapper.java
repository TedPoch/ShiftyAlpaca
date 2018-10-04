package ShiftyAlpaca.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/* This EventWrapper class is a representation of the basic JSON object
    that the Slack API will send regardless of which of the Event API events
    occurs. The Event member is a nested JSON object and contains info specific
    to the relevant Slack Events API callback. (See tedpoch.ShiftyAlpaca.Event.java)
 */
@Data
@Entity
@Table(name = "event_wrappers")
public class EventWrapper implements Serializable {
  @Id     //While EventWrapper uses a Slack Event provided unique string as the PK, here we gen our own
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  private String token;
  private String team_id;
  private String api_app_id;

  @OneToOne (fetch = FetchType.LAZY,
             cascade = CascadeType.ALL,
             mappedBy = "eventWrapper")
  private Event event;

  private String type;

  @Transient
  private List<String> authed_teams;

  //event_id arrives with wrapper and is globally unique across workspaces
  private String event_id;

  private String event_time;

  public EventWrapper() {}
}

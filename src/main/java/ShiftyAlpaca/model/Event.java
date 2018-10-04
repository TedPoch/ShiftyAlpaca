package ShiftyAlpaca.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/*  This class represents the JSON object for a specific Slack Events API
    callback and is always wrapped inside of an EventWrapper when sent from
    Slack and when saved by this application.

 */
@Data
@Entity
@Table (name = "events")
public class Event implements Serializable {
  @Id     //While EventWrapper uses a Slack Event provided unique string as the PK, here we gen our own
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long event_id;

  private String type;
  private String channel;
  private String user;
  private String text;
  private String ts;
  private String event_ts;
  private String channel_type;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wrapper_id", nullable = false)
  private EventWrapper eventWrapper;

  public Event(){}

}

package tedpoch.ShiftyAlpaca.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*  This class represents the JSON object for a specific Slack Events API
    callback and is always wrapped inside of an EventWrapper when sent from
    Slack and when saved by this application.

 */
@Data
@Entity
@Table (name = "events")
public class Event {
  @Id     //While EventWrapper uses a Slack Event provided unique string as the PK, here we gen our own
  @GeneratedValue(generator = "system-uuid") //PK should be random as a security measure
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String app_id;

  private String type;
  private String channel;
  private String user;
  private String text;
  private String ts;
  private String event_ts;
  private String channel_type;
}

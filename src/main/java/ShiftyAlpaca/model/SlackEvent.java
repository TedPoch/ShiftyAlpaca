package ShiftyAlpaca.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/*  This class represents the JSON object for a specific Slack Events API
    callback and is always wrapped inside of an SlackWrapper when sent from
    Slack and when saved by this application.

 */
@Entity
@Table(name = "slack_events")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "event_id")
public class SlackEvent implements Serializable {
  @Id     //While SlackWrapper uses a Slack SlackEvent provided unique string as the PK, here we gen our own
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long event_id;

  private String client_msg_id;
  private String type;
  private String channel;
  private String user;
  private String text;
  private String ts;
  private String event_ts;
  private String channel_type;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wrapper_id", nullable = false)
  //@JsonManagedReference //prevents Jackson inifinite recursion
  private SlackWrapper slackWrapper;

  public SlackEvent(){}

  public Long getEvent_id() {
    return event_id;
  }

  public void setEvent_id(Long event_id) {
    this.event_id = event_id;
  }

  public String getClient_msg_id() {
    return client_msg_id;
  }

  public void setClient_msg_id(String client_msg_id) {
    this.client_msg_id = client_msg_id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getTs() {
    return ts;
  }

  public void setTs(String ts) {
    this.ts = ts;
  }

  public String getEvent_ts() {
    return event_ts;
  }

  public void setEvent_ts(String event_ts) {
    this.event_ts = event_ts;
  }

  public String getChannel_type() {
    return channel_type;
  }

  public void setChannel_type(String channel_type) {
    this.channel_type = channel_type;
  }

  public SlackWrapper getSlackWrapper() {
    return slackWrapper;
  }

  public void setSlackWrapper(SlackWrapper slackWrapper) {
    this.slackWrapper = slackWrapper;
  }
}

package ShiftyAlpaca.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.io.Serializable;

/**  This class represents the JSON object for a specific Slack Events API
 * callback and is always wrapped inside of a SlackWrapper.class object when
 * sent from Slack and when saved by this application (assuming the primary Slack
 * JSON message is of 'type':'event_callback').
 *
 * The JsonIdentityInfo annotation prevents infinite recursion while setting this
 * class's parent member, and that parent member's child object to each other inside of
 * the relevant service.class.
 */
@Entity
@Table(name = "slack_events")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "slack_event_pk")
public class SlackEvent implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long slack_event_pk;

  private String client_msg_id;
  private String type;
  private String channel;
  private String user;
  private String text;
  private String ts;
  private String event_ts;
  private String channel_type;

  /** See comment on SlackEvent member in SlackWrapper.class
   *
   */
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "slack_wrapper_pk", nullable = false)
  private SlackWrapper slackWrapper;

  public SlackEvent(){}

  public Long getSlack_event_pk() {
    return slack_event_pk;
  }

  public void setSlack_event_pk(Long slack_event_pk) {
    this.slack_event_pk = slack_event_pk;
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

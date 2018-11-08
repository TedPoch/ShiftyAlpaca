package ShiftyAlpaca.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** This SlackWrapper class is a representation of the basic JSON object
 * that the Slack API will send regardless of which of the SlackEvent API events
 * occurs. The SlackEvent member is a nested JSON object and contains info specific
 * to the relevant Slack Events API callback. (See tedpoch.ShiftyAlpaca.SlackEvent.java).
 *
 * The JsonIdentityInfo annotation prevents infinite recursion while setting this
 * class's child member, and that child member's parent object to each other inside of
 * the relevant service.class.
 */
@Entity
@Table(name = "slack_wrappers")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "slack_wrapper_pk")
public class SlackWrapper implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long slack_wrapper_pk;

  /** The User.class maintains a List<SlackWrapper> object to track
   * all instances of an EXPLAIN request coming from a specific team
   * member.
   */
  @ManyToOne(cascade = CascadeType.ALL)
  private User user;

  private String token;
  private String team_id;
  private String api_app_id;

  /** The Slack API embeds an 'event' JSON object inside of the primary
   * JSON message it sends to the application (assuming the primary Slack
   * JSON message is of 'type':'event_callback').
   */
  @OneToOne (fetch = FetchType.LAZY,
             cascade = CascadeType.ALL,
             mappedBy = "slackWrapper")
  private SlackEvent event;

  private String type;

  @Transient
  private List<String> authed_users;

  private String event_id;

  private String event_time;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "slackWrapper")
  private List<ExplainResult> explain_results;

  public SlackWrapper() {}

  public Long getSlack_wrapper_pk() {
    return slack_wrapper_pk;
  }

  public void setSlack_wrapper_pk(Long slack_wrapper_pk) {
    this.slack_wrapper_pk = slack_wrapper_pk;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

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

  public SlackEvent getEvent() {
    return event;
  }

  public void setEvent(SlackEvent event) {
    this.event = event;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<String> getAuthed_users() {
    return authed_users;
  }

  public void setAuthed_users(List<String> authed_users) {
    this.authed_users = authed_users;
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

  public List<ExplainResult> getExplain_results() {
    return explain_results;
  }

  public void setExplain_results(List<ExplainResult> explain_results) {
    this.explain_results = explain_results;
  }
}

package ShiftyAlpaca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** This SlackWrapper class is a representation of the basic JSON object
    that the Slack API will send regardless of which of the SlackEvent API events
    occurs. The SlackEvent member is a nested JSON object and contains info specific
    to the relevant Slack Events API callback. (See tedpoch.ShiftyAlpaca.SlackEvent.java)
 */
@Entity
@Table(name = "slack_wrappers")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class SlackWrapper implements Serializable {
  @Id     //While SlackWrapper uses a Slack SlackEvent provided unique string as the PK, here we gen our own
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY,
             optional = false)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private UserIdentity userIdentity;

  private String token;
  private String team_id;
  private String api_app_id;

  @OneToOne (fetch = FetchType.LAZY,
             cascade = CascadeType.ALL,
             mappedBy = "slackWrapper")
  //@JsonBackReference //prevents Jackson infinite recursion
  private SlackEvent event;

  private String type;

  @Transient
  private List<String> authed_users;

  //event_id arrives with wrapper and is globally unique across workspaces
  private String event_id;

  private String event_time;

  public SlackWrapper() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserIdentity getUserIdentity() {
    return userIdentity;
  }

  public void setUserIdentity(UserIdentity user) {
    this.userIdentity = user;
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
}

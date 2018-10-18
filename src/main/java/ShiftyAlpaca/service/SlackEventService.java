package ShiftyAlpaca.service;

import ShiftyAlpaca.model.EventWrapper;
import ShiftyAlpaca.model.QueryResponse;
import ShiftyAlpaca.model.VerificationResponse;
import ShiftyAlpaca.repository.EventWrapperRepo;
//import ShiftyAlpaca.router.QueryDatabase;
//import ShiftyAlpaca.router.QueryDatabaseContextHolder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/** The EventWrapperService (Singleton by virtue of @Service) is
 * stood up by the AnalyzerController. This is where the business
 * logic resides, decoupled from the respective repository. The
 * repo will database the query request, translate/run the EXPLAIN
 * query, and store the result for retrieval by this service class.
 *
 */
@Service  //This makes the service a singleton by default
public class SlackEventService {

  private String URL_BASE = "https://slack.com/api";
  @Value( "${client.id}" )
  private String CLIENT_ID;
  @Value( "${client.secret}" )
  private String CLIENT_SECRET;
  @Value("${bot.token}")
  private String BOT_TOKEN;
  @Autowired
  private EventWrapperRepo eventWrappers;

  /**
   * @param event
   * @return
   */
  public void respond(JsonNode event) {
    EventWrapper eWrapper = new EventWrapper();
    ObjectMapper mapper = new ObjectMapper();
    try {
      //Creating the eventWrapper with the ObjMapper also creates the nested
      //  'Event' object
      //  BUT, we need to set the foreign key of the Event to the parent wrapper
      eWrapper = mapper.readValue(event.toString(), EventWrapper.class);
      eWrapper.getEvent().setEventWrapper(eWrapper);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    //TODO: pickup this mess and finish this weekend
    //QueryDatabaseContextHolder.set(QueryDatabase.TEST_ALPHA);
    eventWrappers.save(eWrapper);

    //translate event text into EXPLAIN
    //String explainSQL = "EXPLAIN " + eWrapper.getEvent().getText() + ";";
    //connect to DB
    //QueryDatabaseContextHolder.set(QueryDatabase.TEST_BETA);
    //run explain

    //store result in above DB save record
    //return recommendation string to user

//    QueryResponse resp = new QueryResponse();
//    resp.setResponse(eWrapper.getEvent().getText());
//    return resp;
    postToUser(eWrapper.getEvent().getChannel(), eWrapper.getEvent().getText());

  }

  /** Handles the Slack API's verification event when ititially establishing the url
   * of this application as the true home of the bot's brains.
   *
   * @param challenge comes in at the controller from Slack. No need to store, just return.
   * @return - This will be the same token sent by the Slack API in the 'challenge' field.
   */
  public VerificationResponse verificationResponse(JsonNode challenge) {
    return new VerificationResponse(challenge.get("challenge").asText());
  }

  /** This method will be called from the Async respond() above in order to post a message
   * back to the Slack user.
   *
   * @param channel - can be the UID for a room or a DM with a user
   * @param text - the response we wish to send to the Slack user requesting our service
   */
  public void postToUser(String channel, String text){
    RestTemplate resp = new RestTemplate();
    MultiValueMap<String, Object> map = new LinkedMultiValueMap();
    map.add("channel", channel);
    map.add("text", text);
    map.add("token", BOT_TOKEN); //TODO: work on saving the token somewhere secure

    //RestTemplate does POST on URL with map object of response data
    resp.postForObject(URL_BASE + "/chat.postMessage", map, String.class);
  }

  public synchronized boolean addEvent(EventWrapper event) {
    //TODO: stub for event service method
    return false;
  }

  public EventWrapper getEventByEventID(String event_id) {
    //TODO: stub for event service method
    return null;
  }

}

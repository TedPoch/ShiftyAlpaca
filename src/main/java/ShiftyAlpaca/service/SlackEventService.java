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

  @Autowired
  private EventWrapperRepo eventWrappers;

  /**
   * @param event
   * @return
   */
  @Async
  public QueryResponse respond(JsonNode event) {
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
    //TODO: pickup this mess and finish on Friday
    //QueryDatabaseContextHolder.set(QueryDatabase.TEST_ALPHA);
    eventWrappers.save(eWrapper);

    //translate event text into EXPLAIN
    String explainSQL = "EXPLAIN " + eWrapper.getEvent().getText() + ";";
    //connect to DB
    //QueryDatabaseContextHolder.set(QueryDatabase.TEST_BETA);
    //run explain

    //store result in above DB save record
    //return recommendation string to user

    QueryResponse resp = new QueryResponse();
    resp.setResponse(eWrapper.getEvent().getText());
    return resp;
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
    MultiValueMap map = new LinkedMultiValueMap();
    map.add("channel", channel);
    map.add("text", text);
    map.add("token", null); //TODO: work on saving the token somewhere secure
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

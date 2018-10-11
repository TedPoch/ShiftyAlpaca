package ShiftyAlpaca.service;

import ShiftyAlpaca.model.EventWrapper;
import ShiftyAlpaca.model.QueryResponse;
import ShiftyAlpaca.model.VerificationResponse;
import ShiftyAlpaca.repository.EventWrapperRepo;
import ShiftyAlpaca.router.QueryDatabase;
import ShiftyAlpaca.router.QueryDatabaseContextHolder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/** The EventWrapperService (Singleton by virtue of @Service) is
 * stood up by the AnalyzerController. This is where the business
 * logic resides, decoupled from the respective repository. The
 * repo will database the query request, translate/run the EXPLAIN
 * query, and store the result for retrieval by this service class.
 *
 */
@Service  //This makes the service a singleton by default
public class EventWrapperService {

  @Autowired
  private EventWrapperRepo eventWrappers;

  public QueryResponse eventResponse(JsonNode event) {
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
    QueryDatabaseContextHolder.set(QueryDatabase.TEST_ALPHA);
    eventWrappers.save(eWrapper);

    //translate event text into EXPLAIN
    String explainSQL = "EXPLAIN " + eWrapper.getEvent().getText() + ";";
    //connect to DB
    QueryDatabaseContextHolder.set(QueryDatabase.TEST_BETA);
    //run explain

    //store result in above DB save record
    //return recommendation string to user

    QueryResponse resp = new QueryResponse();
    resp.setResponse(eWrapper.getEvent().getText());
    return resp;
  }

  public VerificationResponse verificationResponse(JsonNode challenge) {
    return new VerificationResponse(challenge.get("challenge").asText());
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

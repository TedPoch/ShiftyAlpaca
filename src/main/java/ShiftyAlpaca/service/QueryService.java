package ShiftyAlpaca.service;

import ShiftyAlpaca.model.SlackEvent;
import ShiftyAlpaca.model.SlackWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.management.Query;

@Service
public class QueryService {

  private ObjectMapper mapper = new ObjectMapper();

  public QueryService() {}

  public SlackWrapper createWrapper(JsonNode json) {
    SlackWrapper wrapper = new SlackWrapper();
    try {
      wrapper = mapper.readValue(json.toString(), SlackWrapper.class);
    } catch (Exception e) {
      System.out.println("createWrapper() in QueryService failed. SlackWrapper was initialized, but filling it from Json failed.");
    }
    return wrapper;
  }

  public SlackEvent createWrappedEvent(JsonNode json) {
    SlackEvent event = new SlackEvent();
    try {
      event = mapper.readValue(json.get("event").toString(), SlackEvent.class);
    } catch (Exception e) {
      System.out.println("createWrappedEvent() in QueryService failed. SlackEvent was initialized, but filling it from Json failed.");
    }
    return event;
  }

}

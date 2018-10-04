package ShiftyAlpaca.controller;

import ShiftyAlpaca.service.EventWrapperService;
import com.fasterxml.jackson.databind.JsonNode;
import ShiftyAlpaca.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyzerController {
    @Autowired
    private EventWrapperService eventWrapperService;

    /*  The controller accepts JSON objects from the Slack API. These are wrappers
        around a specific event type. The switch case below attempts to determine
        which type of object the API has sent and responds appropriately.

     */
    @PostMapping(value="/analyzer", produces="application/json", consumes="application/json")
    public Response returnResult(@RequestBody JsonNode event) {
      switch (event.get("type").asText()) {
        case "event_callback":
          return eventWrapperService.eventResponse(event);
        case "url_verification":
          return eventWrapperService.verificationResponse(event);
        default:
          return null;
      }


//        switch (event.get("type").asText()) {
//
//            //This case will handle various callbacks and Slack Events API events
//            case "event_callback":
//                ObjectMapper mapper = new ObjectMapper();
//                Map<String, Object> request = mapper.convertValue(event.get("event"), Map.class);
//                return new QueryResponse(request.get("text").toString());
//
//            //Only hits when Slack API needs to re-verify my application server
//            case "url_verification":
//                return new VerificationResponse(event.get("challenge").asText());
//
//            default:
//                return null;
//        }
    }
}

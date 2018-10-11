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

    /**  The controller accepts JSON objects from the Slack API. These are wrappers
        around a specific event type. The switch case below attempts to determine
        which type of object the API has sent and responds appropriately.

     case: event_callback is the basic event that should contain queries for analysis
     case: url_verification is the test run by the Slack API to confirm the application's URL
                  TODO: figure out if Slack runs this more often than just the initial setup
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
    }
}

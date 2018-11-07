package ShiftyAlpaca.controller;

import ShiftyAlpaca.service.SlackEventService;
import com.fasterxml.jackson.databind.JsonNode;
import ShiftyAlpaca.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyzerController {
    @Autowired
    private SlackEventService slackEventService;

    /**  The controller accepts JSON objects from the Slack API. These are wrappers
        around a specific slackEvent type. The switch case below attempts to determine
        which type of object the API has sent and responds appropriately.

     case: event_callback is the basic slackEvent that should contain queries for analysis
     case: url_verification is the test run by the Slack API to confirm the application's URL

     TODO: figure out if Slack runs verify more often than just the initial setup

     */
    //@PostMapping(value="/analyzer", produces="application/json", consumes="application/json")
    @PostMapping(value = "/analyzer", produces = MediaType.TEXT_PLAIN_VALUE)
    public String returnResult(@RequestBody JsonNode event) {

      switch (event.get("type").asText()) {

        case "event_callback":
          //TODO: make this ASYNC so Slack gets 'OK' in timely manner
          slackEventService.respond(event);
          return "Ok";

        case "url_verification":
          return slackEventService.verificationResponse(event).getChallenge();

        default:
          return null;
      }
    }

  /** Only used by the JUnit test for this controller. The autowiring of the Service bean
   * does not occur during the JUnit test, but is needed for the test to work.
   *
   * @param testService
   */
    public void setServiceForTest(SlackEventService testService){
      this.slackEventService = testService;
    }
}

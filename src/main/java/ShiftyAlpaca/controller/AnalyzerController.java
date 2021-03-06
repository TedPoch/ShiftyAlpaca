package ShiftyAlpaca.controller;

import ShiftyAlpaca.service.SlackEventService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzerController {
    @Autowired
    private SlackEventService slackEventService;

    /**  The controller accepts JSON objects from the Slack API. These are wrappers
        around a specific slackEvent type. The switch case below attempts to determine
        which type of object the API has sent and responds appropriately.

     case: event_callback is the basic slackEvent that should contain queries for analysis
     case: url_verification is the test run by the Slack API to confirm the application's URL
              during the initial Slack app setup on your account page
     * @param event - The framework injects the JSON string received from Slack here
     * @return - respond to Slack with either HTTP200 for user queries, or the challenge token if Slack is
     *            verifying the URL of this application
     * @throws Exception - TODO: handle this exception from the Service.class better
     */
    @PostMapping(value = "/analyzer", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity returnResult(@RequestBody JsonNode event) throws Exception {

      switch (event.get("type").asText()) {
        case "event_callback":
          //The respond() method is Async; OK returns while respond() runs in back
          slackEventService.respond(event);
          return new ResponseEntity(HttpStatus.OK);
        case "url_verification":
          return new ResponseEntity<>(slackEventService.verificationResponse(event).getChallenge(), HttpStatus.OK);
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

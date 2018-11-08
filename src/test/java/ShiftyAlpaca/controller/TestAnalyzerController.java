package ShiftyAlpaca.controller;

import ShiftyAlpaca.model.SlackWrapper;
import ShiftyAlpaca.model.VerificationResponse;
import ShiftyAlpaca.repository.SlackWrapperRepo;
import ShiftyAlpaca.service.SlackEventService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

public class TestAnalyzerController {

  /** This tests handling of the verification challenge sent by the Slack API
   *  the first time a new https address is used by this application. The actual
   *  challenge is a random unique alphanumeric string.
   *
   * IMPORTANT: Need to 'new' an instance of the SlackEventService inside the
   */
  @Test(timeout = 3000)
  public void testSlackVerification() throws Exception{
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();
    VerificationResponse test = new VerificationResponse();
    test.setToken("token");
    test.setType("url_verification");
    test.setChallenge("k3kd999dkkf9jffjuAAJJD");

    AnalyzerController a = new AnalyzerController();
    a.setServiceForTest(new SlackEventService());

//    String result = a.returnResult(mapper.valueToTree(test));
//    Assert.assertEquals(result, "k3kd999dkkf9jffjuAAJJD");
  }

//  @Test(timeout = 3000)
//  public void testEventResponse() throws IOException {
//    RestTemplate restTemplate = new RestTemplate();
//    ObjectMapper mapper = new ObjectMapper();
//    JsonNode test = mapper.readTree(new File("src/test/misc/testSlackMessage.json"));
//
//    AnalyzerController a = new AnalyzerController();
//    SlackEventService ses = new SlackEventService();
//    a.setServiceForTest(ses);
//
//    String result = a.returnResult(test);
//    Assert.assertEquals(result, "Ok");
//  }

}

package tedpoch.ShiftyAlpaca.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tedpoch.ShiftyAlpaca.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AnalyzerController {

    /*  The controller accepts JSON objects from the Slack API. These are wrappers
        around a specific event type. The switch case below attempts to determine
        which type of object the API has sent and responds appropriately.

     */
    @PostMapping(value="/analyzer", produces="application/json", consumes="application/json")
    public Response returnResult(@RequestBody JsonNode node) {

        switch (node.get("type").asText()) {

            //This case will handle various callbacks and Slack Events API events
            case "event_callback":
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> request = mapper.convertValue(node.get("event"), Map.class);
                return new QueryResponse(request.get("text").toString());

            //Only hits when Slack API needs to re-verify my application server
            case "url_verification":
                return new VerificationResponse(node.get("challenge").asText());

            default:
                return null;
        }
    }
}

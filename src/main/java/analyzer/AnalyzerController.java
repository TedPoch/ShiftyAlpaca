package analyzer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyzerController {

  @GetMapping("/analyzer")
  public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  @PostMapping(value="/analyzer", consumes="application/json", produces="application/json")
  public VerificationResponse verify (Verification incoming, VerificationResponse resp) {

    resp.setChallenge(incoming.getChallenge());
    return resp;
  }
}
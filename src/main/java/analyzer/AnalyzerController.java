package analyzer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyzerController {

    @PostMapping(value="/analyzer", produces="application/json", consumes="application/json")
    public VerificationResponse doSomeThing(@RequestBody Verification input){
        System.out.println(input.getChallenge());
        return new VerificationResponse(input.getChallenge());
    }
}

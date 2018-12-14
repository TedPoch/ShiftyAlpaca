package ShiftyAlpaca.controller;

import ShiftyAlpaca.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@RestController
public class InstallController {

  @Autowired
  private AppService appService;

  /** This 'install' method presents a user with a webpage with a button to install ShiftyAlpaca
   * to their workspace. It grabs the Slack CLIENT_ID and CLIENT_SECRET from the system variables
   * and inserts them into the link for the install button. See /resources/templates for web pages.
   *
   * @param model - a map containing variables for the templated install page presented to user
   * @return  - web page (View) with install button
   */
  @GetMapping("/install")
  public ModelAndView install(Map<String, Object> model){
    model.put("client_id", System.getenv("CLIENT_ID"));
    return new ModelAndView("install", model);
  }

  /** This 'thanks' method is triggered once the install request above is processed/approved.
   *  The AppService authorize() method is invoked using a 'code' provided from a successful
   *  'install' authorization. The 'code' is exchanged in this method for an OAUTH bot token
   *  used by the app for the remainder of its runtime. Will be replaced when app is reinstalled
   *  and/or restarted. See /resources/templates for web pages.
   *
   * @param code  -   Returned by Slack as part of the process of installing the app to a workspace
   * @return      -   A web page (View) indicating successful install of the app to the workspace.
   */
  @GetMapping("/thanks")
  public ModelAndView thanks(@RequestParam String code) throws IOException {
    appService.authorize(code);
    return new ModelAndView("thanks");
  }
}

package ShiftyAlpaca.controller;

import ShiftyAlpaca.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class InstallController {

  @Autowired
  private AppService appService;

  // TODO: add install method to render "install" view.
  // TODO: install method should attend "/install" using GET
  // TIP: use @GetMapping

  @GetMapping("/install")
  public ModelAndView install(Map<String, Object> model){
    model.put("client_id", System.getenv("CLIENT_ID"));
    return new ModelAndView("install", model);
  }


  // TODO: add thanks method to render "thanks" view.
  // TODO: thanks method should attend "/thanks" using GET
  // TIP: use @GetMapping

  @GetMapping("/thanks")
  public String thanks(@RequestParam String code){
    //TODO: add code to appService

    return "thanks";
  }
}

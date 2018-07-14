package org.eminentstar.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class MvcController {

  @RequestMapping("/hello")
  public void hello() {
  }

  @RequestMapping("/complex")
  public String complex(@RequestParam("name") String name, @CookieValue("auth") String auth,
    ModelMap model) {

    model.put("info", name + "/" + auth);
    return "mvc/myview";
  }
}

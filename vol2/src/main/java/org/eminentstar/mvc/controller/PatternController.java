package org.eminentstar.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/a", "/b"})
public class PatternController {
  @RequestMapping({"/c", "/d"})
  @ResponseBody
  public String hello() {
    return "ok";
  }
}

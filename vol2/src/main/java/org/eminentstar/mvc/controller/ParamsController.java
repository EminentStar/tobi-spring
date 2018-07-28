package org.eminentstar.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(params = {"/p1", "/p2"})
public class ParamsController {
  @RequestMapping(params = {"/p3", "/p4"})
  @ResponseBody
  public String hello() {
    return "ok";
  }
}

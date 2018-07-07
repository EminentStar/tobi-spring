package org.eminentstar.learningtest.spring.controllers;

import java.util.Map;

public class HelloController extends SimpleController {
  public HelloController() {
    this.setRequiredParams(new String[] {"name"});
    this.setViewName("/WEB-INF/view/hello.jsp");
  }

  @Override
  public void control(Map<String, String> params, Map<String, Object> model) {
    model.put("message", "Hello " + params.get("name"));
  }
}

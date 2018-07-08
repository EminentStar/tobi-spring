package org.eminentstar.learningtest.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/annotation")
public class AnnotationHelloController {
  @RequestMapping("/hello")
  public String hello(@RequestParam(defaultValue = "default") String name, ModelMap map) {
    map.put("message", "Hello " + name);
    return "/WEB-INF/view/hello.jsp";
  }
}
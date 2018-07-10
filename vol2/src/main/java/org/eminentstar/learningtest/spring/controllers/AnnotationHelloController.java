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
    return "hello";
  }

  /**
   * 뷰와 관련된 DispatcherServlet 전략중에 `RequestToViewNameTranslator`라는 것이 있음.
   * 컨트롤러가 뷰 이름을 넘겨주지 않았을 경우 URL을 이용해서 자동으로 뷰 이름을 만들어줌.
   */
  @RequestMapping("/translator")
  public void translator(@RequestParam(defaultValue = "default") String name, ModelMap map) {
    map.put("message", "Hello " + name);
  }

}

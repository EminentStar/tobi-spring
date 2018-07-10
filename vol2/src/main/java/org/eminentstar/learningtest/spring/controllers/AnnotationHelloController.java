package org.eminentstar.learningtest.spring.controllers;

import org.eminentstar.exception.NotInServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

  @RequestMapping("/error")
  public String error(ModelMap map) throws IllegalAccessException {
    map.put("message", "Error");
    throw new IllegalAccessException("컨트롤러 혹은 그 뒷 레이어에서 발생한 예외");
  }

  @RequestMapping("/error/unavailable")
  public String unavailable(ModelMap map) {
    throw new NotInServiceException();
  }

  @RequestMapping("/error/interrupted")
  public String interrupted() throws InterruptedException {
    throw new InterruptedException("SimplaMappingExceptionHandler를 통한 예외 등록");
  }

  /**
   * AnnotationMethodExceptionHandler 이용.
   */
  @ExceptionHandler(IllegalAccessException.class)
  public ModelAndView illegalAccessExceptionHandler(IllegalAccessException ex) {
    return new ModelAndView("error/error").addObject("errorMsg", ex.getMessage());
  }

}

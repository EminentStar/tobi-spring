package org.eminentstar.learningtest.spring.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller 인터페이스 타입 기반의 컨트롤러
 */
public abstract class SimpleController implements Controller {
  private String[] requiredParams;
  private String viewName;

  public void setRequiredParams(String[] requiredParams) {
    this.requiredParams = requiredParams;
  }

  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
    if (viewName == null) {
      throw new IllegalStateException(); // viewName 프로퍼티가 지정되지 않았으면 예외 발생
    }

    Map<String, String> params = new HashMap<String, String>();
    for(String param : requiredParams) { // 필요한 파라미터를 가져와 맵에 저장.
      String value = req.getParameter(param);
      if (value == null) {
        throw new IllegalStateException();
      }
      params.put(param, value);
    }

    Map<String, Object> model = new HashMap<String, Object>(); // 모델 오브젝트를 미리 만들어 보낼 준비

    this.control(params, model); // 개별 컨트롤러가 구현할 메소드를 호출.

    // Controller 인터페이스의 정의에 따라 ModelAndView 타입의 결과를 돌려줌.
    return new ModelAndView(this.viewName, model);
  }

  /**
   * 템플릿 메소드 패턴을 사용해서 기반 클래스를 상속받는 개별 컨트롤러가 구현해야 할 메소드를 정의.
   *
   * 서브클래스가 구현할 실제 컨트롤러 로직을 담을 메소드.
   */
  public abstract void control(Map<String, String> params, Map<String, Object> model);

}

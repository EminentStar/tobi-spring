package org.eminentstar.temp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller {
  @Autowired
  HelloSpring helloSpring; // 부모 컨텍스트인 루트 컨텍스트로부터 HelloSpring 빈을 DI 받을 수 있음.

  /**
   * 해당 메소드는 Controller 타입 핸들러를 담당하는 SimpleControllerHandlerAdapter를 통해
   * DispatcherServlet 으로부터 호출될 것임.
   */
  @Override
  public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
    String name = req.getParameter("name"); // 1. 사용자 요청 해석

    String message = this.helloSpring.sayHello(name); // 2. 비즈니스 로직 호출

    Map<String, Object> model = new HashMap<>(); // 3. 모델 정보 생성
    model.put("message", message);

    return new ModelAndView("/WEB-INF/view/hello.jsp", model); // 4. 뷰 지정
  }
}

package org.eminentstar.custommvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

public class SimpleHandlerAdapter implements HandlerAdapter {
  /**
   * 이 핸들러 어댑터가 지원하는 타입을 확인.
   * 하나 이상의 타입을 지원하게 할 수도 있음.
   */
  @Override
  public boolean supports(Object handler) {
    return (handler instanceof SimpleController);
  }

  /**
   *
   */
  @Override
  public ModelAndView handle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    Method m = ReflectionUtils.findMethod(handler.getClass(), "control", Map.class, Map.class);
    ViewName viewName = AnnotationUtils.getAnnotation(m, ViewName.class);
    RequiredParams requiredParams = AnnotationUtils.getAnnotation(m, RequiredParams.class);

    Map<String, String> params = new HashMap<>();
    for(String param : requiredParams.value()) {
      String value = req.getParameter(param);
      if (value == null) {
        throw new IllegalStateException();
      }
      params.put(param, value);
    }

    Map<String, Object> model = new HashMap<>();

    // DispatcherServlet은 컨트롤러의 타입을 모르기 때문에 컨트롤러를 Object 타입으로 넘겨줌.
    // 이를 적절한 컨트롤러 타입으로 캐스팅해서 메소드를 호출해줌.
    ((SimpleController)handler).control(params, model);

    return new ModelAndView(viewName.value(), model);
  }

  /**
   * 컨트롤러의 getLastModified()를 다시 호출해서 컨트롤러가 결정하도록 만듬.
   * 캐싱을 적용하지 않으려면 0보다 작은 값을 리턴.
   */
  @Override
  public long getLastModified(HttpServletRequest httpServletRequest, Object o) {
    return -1;
  }
}

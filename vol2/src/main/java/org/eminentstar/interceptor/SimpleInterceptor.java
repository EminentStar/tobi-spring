package org.eminentstar.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SimpleInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    String name = httpServletRequest.getParameter("name");
    System.out.println(getClass().getName() + " preHandle(): name=" + name);

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView mav) throws Exception {
    Map<String, Object> model = mav.getModel();
    String message = (String)model.get("message");
    System.out.println(getClass().getName() + " postHandle(): message=" + message);

    model.put("message", message + " intercepted");
  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    System.out.println(getClass().getName() + " afterCompletion()");
  }
}

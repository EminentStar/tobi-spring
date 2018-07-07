package org.eminentstar.learningtest.spring.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eminentstar.learningtest.spring.web.AbstractDispatcherServletTest;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

public class ServletControllerTest extends AbstractDispatcherServletTest {
  @Test
  public void helloServletController() throws ServletException, IOException {
    setClasses(SimpleServletHandlerAdapter.class, HelloServlet.class);
    initRequest("/hello").addParameter("name", "Spring");

    assertThat(runService().getContentAsString(), is("Hello Spring"));
  }

  @Component("/hello") // 디폴트인 빈 이름을 이용한 핸들러 매핑 방식을 이용할 수 있도록 빈이름으로 url 설정.
  static class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String name = req.getParameter("name");
      resp.getWriter().print("Hello " + name);
    }
  }
}

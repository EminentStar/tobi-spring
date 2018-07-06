package org.eminentstar.learningtest.spring.web;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.eminentstar.temp.HelloController;
import org.eminentstar.temp.HelloSpring;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.ModelAndView;

public class HelloControllerTest {
  private ConfigurableDispatcherServlet servlet;

  @Test
  public void testHelloControllerWithDispatcherServlet() throws ServletException, IOException {
    // Given
    initDispatcherServlet();
    MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
    req.addParameter("name", "Spring");
    MockHttpServletResponse res = new MockHttpServletResponse();

    // When
    servlet.service(req, res);

    // Then
    ModelAndView mav = servlet.getModelAndView();
    assertThat(mav.getViewName(), is("/WEB-INF/view/hello.jsp"));
    assertThat((String)mav.getModel().get("message"), is("Hello Spring"));
  }

  @Test
  public void testHelloController() throws Exception {
    // Given
    ApplicationContext ac = new GenericXmlApplicationContext("/spring-servlet.xml", "/applicationContext.xml");
    HelloController helloController = ac.getBean(HelloController.class);

    MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
    req.addParameter("name", "Spring");
    MockHttpServletResponse res = new MockHttpServletResponse();

    // When
    ModelAndView mav = helloController.handleRequest(req, res);

    // Then
    assertThat(mav.getViewName(), is("/WEB-INF/view/hello.jsp"));
    assertThat((String)mav.getModel().get("message"), is("Hello Spring"));
  }

  private void initDispatcherServlet() throws ServletException {
    servlet = new ConfigurableDispatcherServlet();

    // 빈 등록
    servlet.setLocations("/spring-servlet.xml");
    servlet.setClasses(HelloSpring.class);

    // 서블릿 초기화(DispatcherServlet은 init()에서 서블릿 컨텍스트를 생성하고 초기화함.)
    servlet.init(new MockServletConfig("spring"));
  }
}

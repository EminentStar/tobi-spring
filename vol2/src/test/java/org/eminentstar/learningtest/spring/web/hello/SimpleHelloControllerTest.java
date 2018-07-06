package org.eminentstar.learningtest.spring.web.hello;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import javax.servlet.ServletException;

import org.eminentstar.learningtest.spring.web.AbstractDispatcherServletTest;
import org.eminentstar.temp.HelloSpring;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class SimpleHelloControllerTest extends AbstractDispatcherServletTest {
  @Test
  public void helloController() throws ServletException, IOException {
    ModelAndView mav = setLocation("/spring-servlet.xml")
      .setClasses(HelloSpring.class)
      .initRequest("/hello", RequestMethod.GET)
      .addParameter("name", "Spring")
      .runService()
      .getModelAndView();

    assertThat(mav.getViewName(), is("/WEB-INF/view/hello.jsp"));
    assertThat((String)mav.getModel().get("message"), is("Hello Spring"));
  }
}

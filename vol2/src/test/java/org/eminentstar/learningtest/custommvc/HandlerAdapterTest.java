package org.eminentstar.learningtest.custommvc;

import java.io.IOException;

import javax.servlet.ServletException;

import org.eminentstar.custommvc.HelloController;
import org.eminentstar.custommvc.SimpleHandlerAdapter;
import org.eminentstar.learningtest.spring.web.AbstractDispatcherServletTest;
import org.junit.Test;

/**
 * FIXME: 실행안됨. 추후 디버깅.
 */
public class HandlerAdapterTest extends AbstractDispatcherServletTest {
  @Test
  public void simpleHandlerAdapter() throws ServletException, IOException {
    // Given
    setClasses(SimpleHandlerAdapter.class, HelloController.class);

    // When
    initRequest("/hello").addParameter("name", "Spring").runService();
    HelloController c = this.getBean(HelloController.class);
    SimpleHandlerAdapter a = this.getBean(SimpleHandlerAdapter.class);

    System.out.println(this.getContentAsString());

    // Then
    assertViewName("/WEB-INF/view/hello.jsp");
    assertModel("message", "Hello Spring");
  }
}

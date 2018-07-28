package org.eminentstar.learningtest.mvc;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@ContextConfiguration("/spring-servlet-requestmappinghandlermapping.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestMappingHandlerMappingTest {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  public void test() {
    // Given
    RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
    assertThat(handlerMapping, is(notNullValue()));

    // When
    for (Map.Entry<RequestMappingInfo, HandlerMethod> hm : handlerMapping.getHandlerMethods().entrySet()) {
      System.out.println(String.format("%s => $s", hm.getKey(), hm.getValue()));
    }

    // Then

  }

}

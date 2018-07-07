package org.eminentstar.learningtest.spring.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.eminentstar.learningtest.spring.controllers.HelloController;
import org.junit.Test;

public class HelloControllerTest {
  @Test
  public void helloControllerTest() {
    // Given
    Map<String, String> params = new HashMap<>();
    params.put("name", "Spring");
    Map<String, Object> model = new HashMap<String, Object>();

    // When
    new HelloController().control(params, model);

    // Then
    assertThat((String)model.get("message"), is("Hello Spring"));

  }
}

package org.eminentstar.learningtest.spring.web.controllers;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.eminentstar.learningtest.spring.controllers.AnnotationHelloController;
import org.junit.Test;
import org.springframework.ui.ModelMap;

public class AnnotationHelloControllerTest {
  @Test
  public void helloControllerTest() {
    // Given
    ModelMap model = new ModelMap();

    // When
    new AnnotationHelloController().hello("Spring", model);

    // Then
    assertThat((String)model.get("message"), is("Hello Spring"));

  }
}

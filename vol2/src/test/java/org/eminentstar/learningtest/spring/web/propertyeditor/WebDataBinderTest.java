package org.eminentstar.learningtest.spring.web.propertyeditor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eminentstar.enumeration.Level;
import org.eminentstar.modelbinding.propertyeditor.LevelPropertyEditor;
import org.junit.Test;
import org.springframework.web.bind.WebDataBinder;

public class WebDataBinderTest {
  @Test
  public void webDataBinderTest() {
    // Given
    WebDataBinder dataBinder = new WebDataBinder(null);
    dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());

    // When
    Level result = dataBinder.convertIfNecessary("1", Level.class);

    // Then
    assertThat(result, is(Level.BASIC));
  }
}

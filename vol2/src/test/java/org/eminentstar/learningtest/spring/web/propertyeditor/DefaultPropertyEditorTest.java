package org.eminentstar.learningtest.spring.web.propertyeditor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.charset.Charset;

import org.junit.Test;
import org.springframework.beans.propertyeditors.CharsetEditor;

public class DefaultPropertyEditorTest {
  @Test
  public void charsetEditor() {
    // Given
    CharsetEditor charsetEditor = new CharsetEditor();

    // When
    charsetEditor.setAsText("UTF-8");

    // Then
    boolean isCharset = charsetEditor.getValue() instanceof Charset;
    assertThat(isCharset, is(true));
    assertThat((Charset)charsetEditor.getValue(), is(Charset.forName("UTF-8")));
  }
}

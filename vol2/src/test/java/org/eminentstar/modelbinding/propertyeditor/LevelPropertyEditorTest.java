package org.eminentstar.modelbinding.propertyeditor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eminentstar.enumeration.Level;
import org.junit.Before;
import org.junit.Test;

public class LevelPropertyEditorTest {
  private LevelPropertyEditor propertyEditor;

  @Before
  public void setUp() {
    propertyEditor = new LevelPropertyEditor();
  }

  @Test
  public void goldTest() {
    // Given

    // When
    propertyEditor.setAsText("3");

    // Then
    assertThat(((Level)propertyEditor.getValue()), is(Level.GOLD));
  }


  @Test
  public void basicTest() {
    // Given

    // When
    propertyEditor.setAsText("1");

    // Then
    assertThat(((Level)propertyEditor.getValue()), is(Level.BASIC));
  }
}

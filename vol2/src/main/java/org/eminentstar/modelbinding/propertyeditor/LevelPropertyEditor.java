package org.eminentstar.modelbinding.propertyeditor;

import java.beans.PropertyEditorSupport;

import org.eminentstar.enumeration.Level;

public class LevelPropertyEditor extends PropertyEditorSupport {
  @Override
  public String getAsText() {
    return String.valueOf(((Level)this.getValue()).intValue());
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    this.setValue(Level.valueOf(Integer.parseInt(text.trim())));
  }
}

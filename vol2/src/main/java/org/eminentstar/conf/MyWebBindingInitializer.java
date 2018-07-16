package org.eminentstar.conf;

import org.eminentstar.enumeration.Level;
import org.eminentstar.propertyeditor.LevelPropertyEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class MyWebBindingInitializer implements WebBindingInitializer {
  @Override
  public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
    webDataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
  }
}

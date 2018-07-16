package org.eminentstar.propertyeditor;

import java.beans.PropertyEditorSupport;

import org.eminentstar.enumeration.Code;
import org.eminentstar.mvc.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CodePropertyEditor extends PropertyEditorSupport {
  @Autowired
  CodeService codeService;

  @Override
  public String getAsText() {
    return String.valueOf(((Code)getValue()).getId());
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    setValue(this.codeService.getCode(Integer.parseInt(text)));
  }
}

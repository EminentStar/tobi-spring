package org.eminentstar.ioc.bean.annotated.autowired;

import org.eminentstar.ioc.bean.Printer;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloWithField {

  private String name;
  @Autowired
  private Printer printer;

  public void setName(String name) {
    this.name = name;
  }

  public Printer getPrinter() {
    return this.printer;
  }

}

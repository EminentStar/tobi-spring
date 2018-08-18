package org.eminentstar.ioc.bean.annotated.autowired;

import org.eminentstar.ioc.bean.Printer;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloWithSetterMethod {

  private String name;
  private Printer printer;

  public void setName(String name) {
    this.name = name;
  }

  @Autowired
  public void setPrinter(Printer printer) {
    this.printer = printer;
  }

  public Printer getPrinter() {
    return this.printer;
  }

}

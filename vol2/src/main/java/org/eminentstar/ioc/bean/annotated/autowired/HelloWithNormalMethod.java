package org.eminentstar.ioc.bean.annotated.autowired;

import org.eminentstar.ioc.bean.Printer;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloWithNormalMethod {

  private String name;
  private Printer printer;
  private Printer printer2;

  @Autowired
  public void config1(Printer printer) {
    this.printer = printer;
    //    this.printer2 = printer2;
  }

  @Autowired
  public void config(Printer printer2) {
    this.printer2 = printer2;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Printer getPrinter() {
    return this.printer;
  }

  public Printer getPrinter2() {
    return this.printer2;
  }

}

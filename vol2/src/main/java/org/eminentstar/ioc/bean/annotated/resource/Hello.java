package org.eminentstar.ioc.bean.annotated.resource;

import javax.annotation.Resource;

import org.eminentstar.ioc.bean.Printer;
import org.springframework.stereotype.Component;

@Component
public class Hello {

  private String name;
  private Printer printer;
  @Resource(name="printer2")
  private Printer printer2;

  public void setName(String name) {
    this.name = name;
  }

  /**
   * <property name="printer" ref="printer"/>와
   * 동일한 의존관계 메타정보로 변환됨.
   */
  @Resource(name = "printer")
  public void setPrinter(Printer printer) {
    this.printer = printer;
  }

  public Printer getPrinter() {
    return this.printer;
  }

  public Printer getPrinter2() {
    return this.printer2;
  }

}

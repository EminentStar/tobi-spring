package org.eminentstar.ioc.bean;

public class Hello {
  String name;
  Printer printer;

  public Hello() {

  }

  public Hello(String name, Printer printer) {
    this.name = name;
    this.printer = printer;
  }

  public String sayHello() {
    return "Hello " + name;
  }

  public void print() {
    this.printer.print(sayHello());
  }

  /**
   * name을 스트링으로 DI 받을 수 있음.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Printer 인터페이스를 구현한 오브젝트를 DI 받음.
   */
  public void setPrinter(Printer printer) {
    this.printer = printer;
  }

  public String getName() {
    return this.name;
  }

  public Printer getPrinter() {
    return this.printer;
  }
}

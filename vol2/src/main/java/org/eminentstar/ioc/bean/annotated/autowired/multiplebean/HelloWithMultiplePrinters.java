package org.eminentstar.ioc.bean.annotated.autowired.multiplebean;

import java.util.Collection;
import java.util.Map;

import org.eminentstar.ioc.bean.Printer;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloWithMultiplePrinters {

  private String name;
  @Autowired
  Collection<Printer> printers;

  @Autowired
  Printer[] printerArray;

  @Autowired
  Map<String, Printer> printerMap;

  public void setName(String name) {
    this.name = name;
  }

  public Collection<Printer> getPrinters() {
    return printers;
  }

  public Printer[] getPrinterArray() {
    return printerArray;
  }

  public Map<String, Printer> getPrinterMap() {
    return printerMap;
  }

}

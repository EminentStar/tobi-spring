package org.eminentstar.ioc.bean.stereotype;

import org.eminentstar.ioc.bean.Printer;
import org.springframework.stereotype.Component;

@Component("printer")
public class StringPrinter implements Printer {
  private StringBuffer buffer = new StringBuffer();

  @Override
  public void print(String message) {
    this.buffer.append(message);
  }

  @Override
  public String toString() {
    return this.buffer.toString();
  }
}

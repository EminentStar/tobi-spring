package org.eminentstar.ioc.bean.annotated.autowired.qualifier;

import org.eminentstar.ioc.bean.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class HelloWithQualifier {

  private String name;
  private DataSource dataSource;
  @Autowired(required = false)
  private Printer printer;

  @Autowired
  public void config(@Qualifier("mainDB") DataSource dataSource, Printer printer) {
    this.dataSource = dataSource;
    this.printer = printer;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Printer getPrinter() {
    return this.printer;
  }

  public DataSource getDataSource() {
    return this.dataSource;
  }

}

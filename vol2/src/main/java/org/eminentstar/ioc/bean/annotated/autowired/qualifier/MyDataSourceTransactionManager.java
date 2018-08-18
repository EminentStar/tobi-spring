package org.eminentstar.ioc.bean.annotated.autowired.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MyDataSourceTransactionManager {
  @Autowired
  @Qualifier("mainDB")
  private DataSource dataSource;

  public DataSource getDataSource() {
    return this.dataSource;
  }
}

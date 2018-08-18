package org.eminentstar.ioc.bean.annotated.autowired.qualifier;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("subDB")
public class MySQLDataSource implements DataSource {
  @Override
  public Connection getConnection() throws SQLException {
    return null;
  }
}

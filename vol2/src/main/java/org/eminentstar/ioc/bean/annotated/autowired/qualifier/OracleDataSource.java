package org.eminentstar.ioc.bean.annotated.autowired.qualifier;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("mainDB") // <qualifier value="mainDB"/> 와 동일
public class OracleDataSource implements DataSource {
  @Override
  public Connection getConnection() throws SQLException {
    return null;
  }
}

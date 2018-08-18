package org.eminentstar.ioc.bean.annotated.autowired.qualifier;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource {
  Connection getConnection() throws SQLException;
}

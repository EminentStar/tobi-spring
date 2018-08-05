package org.eminentstar.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * applicationContextWith SimpleDriverDataSource.xml과 비교.
 */
@Configuration
public class ServiceConfig {

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
    dataSource.setUrl("jdbc:mysql://localhost/testdb");
    dataSource.setUsername("spring");
    dataSource.setPassword("book");

    return dataSource;
  }

}

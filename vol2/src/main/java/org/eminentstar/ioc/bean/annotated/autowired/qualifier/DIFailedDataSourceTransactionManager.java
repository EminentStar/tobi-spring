package org.eminentstar.ioc.bean.annotated.autowired.qualifier;

public class DIFailedDataSourceTransactionManager {
  /*
   * Caused by: org.springframework.beans.factory.BeanCreationException:
   * Error creating bean with name 'diFailedDataSourceTransactionManager':
   * Injection of autowired dependencies failed; nested exception is
   * org.springframework.beans.factory.BeanCreationException:
   * Could not autowire field: private org.eminentstar.ioc.bean.annotated.autowired.qualifier.DataSource
   * org.eminentstar.ioc.bean.annotated.autowired.qualifier.DIFailedDataSourceTransactionManager.dataSource;
   * nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException:
   * No unique bean of type [org.eminentstar.ioc.bean.annotated.autowired.qualifier.DataSource] is defined:
   * expected single matching bean but found 2: [mySQLDataSource, oracleDataSource]
   */
  //  @Autowired
//  private DataSource dataSource;
}

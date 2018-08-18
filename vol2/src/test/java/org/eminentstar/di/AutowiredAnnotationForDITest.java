package org.eminentstar.di;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.collection.IsMapContaining.*;

import java.util.Collection;
import java.util.Map;

import org.eminentstar.ioc.bean.Printer;
import org.eminentstar.ioc.bean.annotated.autowired.HelloWithConstructor;
import org.eminentstar.ioc.bean.annotated.autowired.HelloWithField;
import org.eminentstar.ioc.bean.annotated.autowired.HelloWithNormalMethod;
import org.eminentstar.ioc.bean.annotated.autowired.HelloWithSetterMethod;
import org.eminentstar.ioc.bean.annotated.autowired.multiplebean.HelloWithMultiplePrinters;
import org.eminentstar.ioc.bean.annotated.autowired.qualifier.DIFailedDataSourceTransactionManager;
import org.eminentstar.ioc.bean.annotated.autowired.qualifier.DataSource;
import org.eminentstar.ioc.bean.annotated.autowired.qualifier.HelloWithQualifier;
import org.eminentstar.ioc.bean.annotated.autowired.qualifier.MyDataSourceTransactionManager;
import org.eminentstar.ioc.bean.annotated.autowired.qualifier.OracleDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/di/applicationContextWithAutowiredAnnotationForDI.xml")
public class AutowiredAnnotationForDITest {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void autowiredAnnotationDIWithSetterMethod() {
    // Given
    HelloWithSetterMethod hello = applicationContext.getBean("helloWithSetterMethod", HelloWithSetterMethod.class);

    // When
    Printer printer = hello.getPrinter();

    // Then
    assertThat(printer, is(notNullValue()));
  }

  @Test
  public void autowiredAnnotationDIWithField() {
    // Given
    HelloWithField hello = applicationContext.getBean("helloWithField", HelloWithField.class);

    // When
    Printer printer = hello.getPrinter();

    // Then
    assertThat(printer, is(notNullValue()));
  }

  @Test
  public void autowiredAnnotationDIWithConstructor() {
    // Given
    HelloWithConstructor hello = applicationContext.getBean("helloWithConstructor", HelloWithConstructor.class);

    // When
    Printer printer = hello.getPrinter();
    Printer printer2 = hello.getPrinter2();

    // Then
    assertThat(printer, is(notNullValue()));
    assertThat(printer2, is(notNullValue()));
  }

  @Test
  public void autowiredAnnotationDIWithNormalMethod() {
    // Given
    HelloWithNormalMethod hello = applicationContext.getBean("helloWithNormalMethod", HelloWithNormalMethod.class);

    // When
    Printer printer = hello.getPrinter();
    Printer printer2 = hello.getPrinter2();

    // Then
    assertThat(printer, is(notNullValue()));
    assertThat(printer2, is(notNullValue()));
  }

  @Test
  public void autowiredAnnotationDIWithMultipleBeanCandidates() {
    // Given
    HelloWithMultiplePrinters hello = applicationContext.getBean("helloWithMultiplePrinters", HelloWithMultiplePrinters.class);

    // When
    Collection<Printer> printers = hello.getPrinters();
    Printer[] printerArray = hello.getPrinterArray();

    Map<String, Printer> printerMap = hello.getPrinterMap();

    // Then
    assertThat(printers.size(), is(2));
    assertThat(printerArray.length, is(2));

    assertThat(printerMap.size(), is(2));
    assertThat(printerMap, is(hasKey("printer")));
    assertThat(printerMap, is(hasKey("printer2")));
  }

  /**
   * application context 초기화자체에 실패해서 이건 뭐 잡을 수 없음.
   */
  @Test(expected = Exception.class)
  public void dependencyInjectionFAILEDWhenThereAreMoreThanTwoBeanObjectOfSameType() {
    // Given
    DIFailedDataSourceTransactionManager transactionManager =
      applicationContext.getBean("dIFailedDataSourceTransactionManager", DIFailedDataSourceTransactionManager.class);

    // When

    // Then

  }

  @Test
  public void dependencyInjectionSUCCESSWhenThereAreMoreThanTwoBeanObjectOfSameType() {
    // Given
    MyDataSourceTransactionManager transactionManager =
      applicationContext.getBean("myDataSourceTransactionManager", MyDataSourceTransactionManager.class);

    // When
    DataSource dataSource = transactionManager.getDataSource();

    // Then
    assertThat(dataSource instanceof OracleDataSource, is(true));
  }

  @Test
  public void qualifierInNormalMethodParameter() {
    // Given
    HelloWithQualifier hello = applicationContext.getBean("helloWithQualifier", HelloWithQualifier.class);

    // When
    DataSource dataSource = hello.getDataSource();

    // Then
    assertThat(dataSource instanceof OracleDataSource, is(true));

  }

}

package org.eminentstar.conf.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.eminentstar.conf.ApiConfig;
import org.eminentstar.conf.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ServletInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // 직접 컨텍스트 오브젝트 생성
    AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();

    // @Configuration 클래스 정보 등록
//    ac.scan("org.eminentstar.conf");
    // register()를 통해서 @Configuration 클래스 하나만 등록할 수도 있음.
    ac.register(ApiConfig.class);

    // 컨텍스트 오브젝트를 ContextLoaderListener의 생성자 파라미터로 전달해서 리스너를 만들어 등록
    ServletContextListener listener = new ContextLoaderListener(ac);
    servletContext.addListener(listener);

    addDispatcherServlet(servletContext);
  }

  private void addDispatcherServlet(ServletContext servletContext) {
    AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
    dispatcherContext.register(WebConfig.class); // WebConfig 클래스를 기본 설정정보로 사용하는 서블릿 컨텍스트 등록

    DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
  }
}

package org.eminentstar.conf.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ServletInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // 직접 컨텍스트 오브젝트 생성
    AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();

    // @Configuration 클래스 정보 등록
    ac.scan("org.eminentstar.conf");
    // register()를 통해서 @Configuration 클래스 하나만 등록할 수도 있음.
//    ac.register(ApiConfig.class);

    // 컨텍스트 오브젝트를 ContextLoaderListener의 생성자 파라미터로 전달해서 리스너를 만들어 등록
    ServletContextListener listener = new ContextLoaderListener(ac);
    servletContext.addListener(listener);
  }
}

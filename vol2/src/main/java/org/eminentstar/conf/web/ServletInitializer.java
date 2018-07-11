package org.eminentstar.conf.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;

public class ServletInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    /*
     * 서블릿 컨텍스트 종료 시점은 파악할 수 없기때문에,
     * WebApplicationInitializer를 사용하더라도 루트 컨텍스트는 리스너를 이용해 관리하는게 좋음.
     * */
    ServletContextListener listener = new ContextLoaderListener();
    servletContext.addListener(listener);

    /*
    * web.xml에서의 컨텍스트 파라미터 설정 부분
    * */
    servletContext.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
    servletContext.setInitParameter("contextConfigLocation", "org.eminentstar.conf");
  }
}

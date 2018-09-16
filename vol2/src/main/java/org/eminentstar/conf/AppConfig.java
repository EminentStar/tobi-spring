package org.eminentstar.conf;

import org.eminentstar.ioc.bean.stereotype.Hello;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(
  basePackages = "org.eminentstar.ioc.bean.stereotype",
  excludeFilters = {
    @ComponentScan.Filter({
      Service.class
    }),
    @ComponentScan.Filter(
      type=FilterType.ASSIGNABLE_TYPE,
      value=Hello.class
    )
  }
)
public class AppConfig {
}

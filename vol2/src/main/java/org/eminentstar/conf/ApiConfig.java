package org.eminentstar.conf;

import org.eminentstar.validator.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

// 생성된 루트 컨텍스트에서 런타임 환경 오브젝트를 가져와서
// 활성 프로파일을 지정하거나 프로퍼티 소스를 추가할 수도 있음.
//@Profile({"local", "dev", "stage", "real"})
@Configuration
@ComponentScan(
  basePackages = {"org.eminentstar"},
  excludeFilters = @ComponentScan.Filter({
    Controller.class
  })
)
public class ApiConfig {
  @Bean
  public UserValidator getUserValidator() {
    return new UserValidator();
  }

  @Bean
  public LocalValidatorFactoryBean getLocalValidatorFactoryBean() {
    return new LocalValidatorFactoryBean();
  }

}

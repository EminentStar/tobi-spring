package org.eminentstar.mvc.controller;

import javax.validation.Valid;

import org.eminentstar.mvc.model.User;
import org.eminentstar.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserValidator validator;
  @Autowired
  LocalValidatorFactoryBean localValidatorFactoryBean;

  /**
   * User 클래스에 default constructor를 추가해주지 않고 실행을 시키니 다음과 같은 에러가 떴음.
   *
   * Message Request processing failed;
   * nested exception is org.springframework.beans.BeanInstantiationException:
   * Could not instantiate bean class [org.eminentstar.mvc.model.User]:
   * No default constructor found; nested exception is java.lang.NoSuchMethodException:
   * org.eminentstar.mvc.model.User.<init>()
   *
   * 컨트롤러 메소드 호출시 스프링에서 해당 모델 오브젝트를 생성하는데 이때 디폴트 생성자를 사용하는듯.
   *
   */
  @RequestMapping("/add")
  @ResponseBody
  public String add(@ModelAttribute User user, BindingResult result) {
    this.validator.validate(user, result);
    if (result.hasErrors()) {
      return "error";
    } else {
      return "success";
    }
  }

  @RequestMapping("/add2")
  @ResponseBody
  public String add2(@ModelAttribute @Valid User user, BindingResult result) {
    this.validator.validate(user, result);
    if (result.hasErrors()) {
      return "error";
    } else {
      return "success";
    }
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
//    webDataBinder.setValidator(this.validator);
    webDataBinder.setValidator(this.localValidatorFactoryBean);
  }
}

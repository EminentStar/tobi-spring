package org.eminentstar.mvc.controller;

import org.eminentstar.mvc.model.Result;
import org.eminentstar.mvc.model.User;
import org.eminentstar.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index() {
    return "ajax/index";
  }

  @RequestMapping(value = "checkloginid/{loginId}", method = RequestMethod.GET)
  @ResponseBody
  public Result checklogin(@PathVariable String loginId) {
    Result result = new Result();

    if (userService.isRegisteredLoginId(loginId)) {
      result.setDuplicated(true);
      result.setAvailableId(loginId + (int)(Math.random() * 1000));
    } else {
      result.setDuplicated(false);
    }

    /*
    * 이 오브젝트는 @ResponseBody 설정에 따라 MappingJacksonHttpMessageConverter에 넘겨지고
    * JSON 메시지로 만들어져 HTTP response body로 설정되 클라이언트로 전달됨.
    * */
    return result;
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String form() {
    return "ajax/form";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public User registerPost(@RequestBody User user) {
    user.setAge(30);
    return user;
  }

}

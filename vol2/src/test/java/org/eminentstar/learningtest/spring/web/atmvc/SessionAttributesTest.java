package org.eminentstar.learningtest.spring.web.atmvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.eminentstar.learningtest.spring.web.AbstractDispatcherServletTest;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.Data;

public class SessionAttributesTest extends AbstractDispatcherServletTest {

  /**
   * FIXME
   */
  @Test
  public void sessionAttr() throws ServletException, IOException {
    setClasses(UserController.class);
    // GET 요청
    initRequest("/user/edit").addParameter("id", "1");
    runService();

    HttpSession session = request.getSession();
    assertThat(session.getAttribute("user"), is(getModelAndView().getModel().get(("user"))));

    // POST 요청
    initRequest("/user/edit", "POST")
      .addParameter("id", "1")
      .addParameter("name", "Spring2");
    runService();

    assertThat(((User)getModelAndView().getModel().get("user"))
      .getEmail(), is("mail.spring.com"));
  }

  @Controller
  @SessionAttributes("user")
  static class UserController {
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public User form(@RequestParam int id) {
      return new User(1, "Spring", "mail@spring.com");
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public void submit(@ModelAttribute User user, SessionStatus sessionStatus) {
      sessionStatus.setComplete();
    }
  }

  @Data
  static class User {
    int id;
    String name;
    String email;

    public User(int id, String name, String email) {
      this.id = id;
      this.name = name;
      this.email = email;
    }
  }
}

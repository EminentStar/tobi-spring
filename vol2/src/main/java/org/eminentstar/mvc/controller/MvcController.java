package org.eminentstar.mvc.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class MvcController {

  @RequestMapping("/hello")
  public void hello() {
  }

  @RequestMapping("/complex")
  public String complex(@RequestParam("name") String name, @CookieValue("auth") String auth,
    ModelMap model) {

    model.put("info", name + "/" + auth);
    return "mvc/myview";
  }

  /**
   * Spring Web MVC Framework
   *
   * In Supported method return types
   *
   * `void`:::
   * if the method handles the response itself (by writing the response content directly,
   * declaring an argument of type ServletResponse / HttpServletResponse for that purpose)
   * or if the view name is supposed to be implicitly determined through a `RequestToViewNameTranslator`
   * (not declaring a response argument in the handler method signature).
   */
  @RequestMapping("/javaee")
  public void javaee(HttpServletRequest req
    //    , HttpServletResponse res
    , ModelMap model) {
    String info = "";

    for (String header : Collections.list(req.getHeaderNames())) {
      info += header + ": " + req.getHeader(header) + "<br>";
    }

    model.put("info", info);
  }

  @RequestMapping("/httpsession")
  public void httpsession(HttpSession session, ModelMap model) {
    model.put("info", session.toString());
  }

  @RequestMapping("/user/view/{id}")
  public String singlePathVariable(@PathVariable("id") int id, ModelMap model) {
    model.put("id", id);

    return "mvc/pathVariable";
  }

  @RequestMapping("/user/view/{id}/{name}")
  public String multiplePathVariable(@PathVariable("id") int id, @PathVariable("name") String name,
    ModelMap model) {

    model.put("id", id);
    model.put("name", name);

    return "mvc/pathVariable";
  }

  @RequestMapping("/params")
  public void params(@RequestParam Map<String, String> params, ModelMap model) {
    String info = "";

    for (Map.Entry<String, String> entry : params.entrySet()) {
      info += entry.getKey() + ": " + entry.getValue() + "<br>";
    }

    model.put("info", info);
  }

  @RequestMapping("/param/default")
  public String defaultParam(@RequestParam(defaultValue = "defaultName") String name, ModelMap model) {
    model.put("name", name);
    return "mvc/param";
  }

  @RequestMapping("/param/required/false")
  public String optionalParam(@RequestParam(required = false) String name, ModelMap model) {
    model.put("name", name);
    return "mvc/param";
  }

  @RequestMapping("/param/omit")
  public String omittedParamAnnotation(String name, ModelMap model) {
    model.put("name", name);
    return "mvc/param";
  }

  @RequestMapping("/header")
  public void header(@RequestHeader("Host") String host,
    @RequestHeader("User-Agent") String userAgent,
    @RequestHeader(value = "Cookie", required = false, defaultValue = "NONE") String cookie,
    ModelMap model) {

    model.put("host", host);
    model.put("userAgent", userAgent);
    model.put("cookie", cookie);
  }

}

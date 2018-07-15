package org.eminentstar.mvc.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eminentstar.mvc.model.UserForm;
import org.eminentstar.mvc.model.UserSearch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping("/model/autoname")
  public String modelAutoName(ModelMap model) {
    User user = new User(1, "junkyu");
    String message = "message bulabula";
    // TODO: jsp에서 왜 user.id/user.name으로 접근이 안되지? getter 넣어도 안되네. 접근 제어자 문젠가?
    model.addAttribute(user);
    model.addAttribute(message); // String도 타입정보 체크해서 모델 이름을 `string`이라고 하는구나,

    return "mvc/model";
  }

  @RequestMapping(value = "/form", method = RequestMethod.GET)
  public void form(ModelMap model) {
    model.put("message", "form");
  }

  /**
   * form의 필드는 @RequestParam으로 받을 수 있음.
   */
  @RequestMapping(value = "/form", method = RequestMethod.POST)
  public String submit(@RequestParam String name, ModelMap model) {
    model.put("name", name);
    return "mvc/form";
  }

  /**
   * 검색 페이지는 데이터가 바뀌지 않는 한 반복해서 접근가능해야하고 북마크 가능해야함
   * so, GET 메소드에 URL 쿼리 스트링의 파라미터로 전달될 것임.
   */
  @RequestMapping(value = "/user/v1/search")
  public String searchWithRequestParam(
    @RequestParam int id, @RequestParam String name, @RequestParam int level
    , @RequestParam String email, ModelMap model) {

    model.put("id", id);
    model.put("name", name);
    model.put("level", level);
    model.put("email", email);

    return "mvc/search";
  }

  /**
   * 여러 개의 정보를 담을 수 있는 오브젝트에 모든 검색조건을 넣는 편이 좋음.
   */
  @RequestMapping(value = "/user/v2/search")
  public String searchWithModelAttribute(@ModelAttribute("searchParam") UserSearch searchParam, ModelMap model) {
    return "mvc/search";
  }

  @RequestMapping(value = "/user/form", method = RequestMethod.GET)
  public String userForm(@ModelAttribute("form") UserForm form, @RequestParam(required = false, defaultValue = "false") boolean isEdit) {
    if (isEdit) { // FIXME: form field 태그에 세팅하는 건 아직 공부를 못함. 아마 커스텀 태그를 사용해야하나?
      form.setId(3843);
      form.setName("eminentstar");
      form.setNickname("passionistar");
    }

    return "mvc/user/form";
  }

  @RequestMapping(value = "/user/form", method = RequestMethod.POST)
  public String userSubmit(@ModelAttribute("form") UserForm form
    , BindingResult bindingResult, ModelMap model) {

    String errorMessage = "";

    for (FieldError error : bindingResult.getFieldErrors()) {
      errorMessage += "Field: " + error.getField() + "/" + error.getRejectedValue() + "<br>";
    }

    model.put("errorCount", bindingResult.getErrorCount());
    model.put("errorMessage", errorMessage);

    return "mvc/user/result";
  }

  @RequestMapping(value = "/user/form2", method = RequestMethod.GET)
  public String userForm2(@ModelAttribute("form") UserForm form) {
    return "mvc/user/form2";
  }

  @RequestMapping(value = "/user/form2", method = RequestMethod.POST)
  public String userSubmit2(@ModelAttribute("form") UserForm form, @RequestBody String body, ModelMap model) {
    model.put("body", body);
    return "mvc/user/result2";
  }

  /*
   * TODO: @Value 사용방법은 다음에 configuration에 대한 공부를 좀 하고 완료해보자.
   */
  //  @RequestMapping("/property/v1")
//  public String propertyIntoParameter(@Value("${os.name}") String osName, ModelMap model) {
//    model.put("osName", osName);
//    return "mvc/property";
//  }
//  @RequestMapping("/property/v2")
//  public String propertyInMemberField(ModelMap model) {
//    model.put("osName", osName);
//    return "mvc/property";
//  }

  class User {
    int id;
    String name;

    public User(int id, String name) {
      this.id = id;
      this.name = name;
    }

    //    public int getId() {
    //      return id;
    //    }
  }

}

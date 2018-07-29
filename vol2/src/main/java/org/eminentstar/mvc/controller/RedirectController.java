package org.eminentstar.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/redirect")
public class RedirectController {

  @RequestMapping("/index")
  @ResponseBody
  public String index(@RequestParam String id) {
    return "id: " + id;
  }

  @RequestMapping("/do/{id}")
  public String doRedirect(@PathVariable String id, ModelMap model) {
    model.put("id", id);

    return "redirect:/redirect/index";
  }

  @RequestMapping("/do/nonpath/{id}")
  public String doRedirectWithNoPathVariable() {
    return "redirect:/redirect/index?id={id}";
  }

  @RequestMapping("/uri")
  @ResponseBody
  public String showUri(HttpServletRequest request) {
    return request.getRequestURI();
  }

  @RequestMapping("/do/unnecessary/model")
  public String doRedirectWithUnnecessaryModel() {
    return "redirect:/redirect/uri";
  }

  /**
   * 핸들러 매핑과 핸들러 어댑터가 3.1에서 새로나온 RequestMapping RequestMapping/HandlerAdapter여야 호출이 가능함.
   */
  @RequestMapping("/do/redirectAttr")
  public String doRedirectWithRedirectAttributes(RedirectAttributes ra) {
    ra.addAttribute("status", "ok");
    return "redirect:/redirect/uri";
  }

  @ModelAttribute("level")
  public String getLevel() {
    return "BASIC";
  }
}

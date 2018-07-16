package org.eminentstar.mvc.controller;

import javax.inject.Inject;
import javax.inject.Provider;

import org.eminentstar.enumeration.Code;
import org.eminentstar.enumeration.Level;
import org.eminentstar.mvc.model.CodeUser;
import org.eminentstar.mvc.model.Member;
import org.eminentstar.propertyeditor.CodePropertyEditor;
import org.eminentstar.propertyeditor.MinMaxPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/propertyeditor")
public class PropertyEditorController {
  @Inject
  Provider<CodePropertyEditor> codeEditorProvider;

  /**
   * Level enum 용 PropertyEditor를 제공해주지 않으면
   * /user/search?level=1 과 같은 형식으로 요청이 오면
   * ConversionNotSupportedException 예외 때문에 500 에러가 날 것임.
   *
   * 커스텀 프로퍼티 에디터를 추가해주지 않고, 문자열으로 enum 엘리먼트를 보내면
   * 디폴트 프로퍼티 에디터에서 프로퍼티 처리해주는게 있나봄.
   */
  @RequestMapping("/user/search")
  public String search(@RequestParam Level level, ModelMap model) {
    model.put("level", level);

    return "propertyeditor/hello";
  }

  /**
   * e.g. /propertyeditor/add?id=1000&age=1000
   */
  @RequestMapping("/add")
  @ResponseBody
  public String add(@ModelAttribute Member member) {
    return "id: " + member.getId() + ", age: " + member.getAge();
  }

  @RequestMapping("/add/codeuser")
  @ResponseBody
  public String addCodeUser(@ModelAttribute CodeUser codeUser) {
    return codeUser.toString();
  }

  @InitBinder
  public void initBinder(WebDataBinder dataBinder) {
//    dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor()); // bind to this controller
    dataBinder.registerCustomEditor(int.class, "age", new MinMaxPropertyEditor(0, 200));
    // Provider를 이용해 프로토타입 빈의 새 오브젝트를 가져옴
    dataBinder.registerCustomEditor(Code.class, codeEditorProvider.get());
  }
}

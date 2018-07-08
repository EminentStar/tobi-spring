package org.eminentstar.custommvc;

import java.util.Map;

public class HelloController implements SimpleController {
  @ViewName("/WEB-INF/view/hello.jsp")
  @RequiredParams({"name"})
  @Override
  public void control(Map<String, String> params, Map<String, Object> model) {
    model.put("message", "Hello " + params.get("name"));
  }
}

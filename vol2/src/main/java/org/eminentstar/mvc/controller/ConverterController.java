package org.eminentstar.mvc.controller;

import org.eminentstar.enumeration.Level;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/converter")
public class ConverterController {

//  @Autowired
//  ConversionService conversionService;

  @RequestMapping("/user/search")
  @ResponseBody
  public String search(@RequestParam Level level) {

    return "level: " + level.name();
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
//    webDataBinder.setConversionService(this.conversionService);
  }
}

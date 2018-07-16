package org.eminentstar.mvc.service;

import org.eminentstar.enumeration.Code;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

  public Code getCode(int codeId) {
    return Code.valueOf(codeId);
  }
}

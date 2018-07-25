package org.eminentstar.mvc.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private static final List<String> registeredId = Arrays.asList("eminent.star", "developer");

  public boolean isRegisteredLoginId(String loginId) {
    return registeredId.contains(loginId);
  }
}

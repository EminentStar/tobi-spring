package org.eminentstar.mvc.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TempService {

  public List<Integer> getIds() {
    return Arrays.asList(1, 2, 3, 4, 5);
  }
}

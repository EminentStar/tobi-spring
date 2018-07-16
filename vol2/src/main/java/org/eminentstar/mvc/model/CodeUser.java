package org.eminentstar.mvc.model;

import org.eminentstar.enumeration.Code;

import lombok.Data;

@Data
public class CodeUser {
  int id;
  String name;
  Code userType;
}

package org.eminentstar.mvc.model;

import lombok.Data;

@Data
public class User {
  private int id;
//  @NotNull
//  @Size(min=1)
  private String name;
//  @Min(0)
  private int age;

  public User() {

  }

  public User(int id, String name) {
    this.id = id;
    this.name = name;
  }
}

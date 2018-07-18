package org.eminentstar.mvc.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class User {
  private int id;
  @NotNull
  @Size(min=1)
  private String name;
  @Min(0)
  private int age;

  public User() {

  }

  public User(int id, String name) {
    this.id = id;
    this.name = name;
  }
}

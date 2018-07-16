package org.eminentstar.enumeration;

public enum Code {
  GOLD(3, "골드"), SILVER(2, "실버"), BASIC(1, "베이직"); // 세 개의 enum object 정의

  private final int id;
  private final String name;

  Code(int value, String name) { // DB에 저장할 값을 넣어줄 생성자를 만들어둔다.
    this.id = value;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public static Code valueOf(int value) {
    switch (value) {
      case 1:
        return BASIC;
      case 2:
        return SILVER;
      case 3:
        return GOLD;
      default:
        throw new AssertionError("Unknown id: " + value);

    }
  }
}


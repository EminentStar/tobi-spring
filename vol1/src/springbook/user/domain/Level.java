package springbook.user.domain;

public enum Level {
  GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER); // 세 개의 enum object 정의

  private final int value;
  private final Level next;

  Level(int value, Level next) { // DB에 저장할 값을 넣어줄 생성자를 만들어둔다.
    this.value = value;
    this.next = next;
  }

  public int intValue() {
    return value;
  }

  /**
   * Level의 순서를 UserService보단 Leevl에 맡기자.
   */
  public Level nextLevel() {
    return this.next;
  }

  /**
   * 값으로부터 Level 타입 오브젝트를 가져오도록 만든 스태틱 메서드
   */
  public static Level valueOf(int value) {
    switch (value) {
      case 1:
        return BASIC;
      case 2:
        return SILVER;
      case 3:
        return GOLD;
      default:
        throw new AssertionError("Unknown value: " + value);

    }
  }
}

package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/***
 * 연결횟수 카운팅 기능이 있는 클래스
 */
public class CountingConnectionMaker implements ConnectionMaker {
  int counter = 0;
  private ConnectionMaker realConnectionMaker;

  public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
    this.realConnectionMaker = realConnectionMaker;
  }

  public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
    this.counter++;
    return realConnectionMaker.makeNewConnection();
  }

  public int getCounter() {
    return this.counter;
  }
}

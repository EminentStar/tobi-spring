package springbook.user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {
  private ConnectionMaker connectionMaker;

  public AccountDao(ConnectionMaker connectionMaker) {
    this.connectionMaker = connectionMaker;
  }
}


package springbook.learningtest.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "spring.xml")
public class JdbcTemplateTest {
  @Autowired
  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate;

  @Before
  public void setUp() {
    this.jdbcTemplate = new JdbcTemplate();
    this.jdbcTemplate.setDataSource(this.dataSource);
  }

  @Test
  public void resultOfQueryMethodWhenThereIsNoRowIsEmptyList() {
    List<User> result = this.jdbcTemplate.query("SELECT * FROM users WHERE id = 'unknown_id' ORDER BY id",
      new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
          User user = new User();
          user.setId(rs.getString("id"));
          user.setName(rs.getString("name"));
          user.setPassword(rs.getString("password"));
          return user;
        }
      });

    assertThat(result.size(), is(0));
    assertTrue(CollectionUtils.isEmpty(result));
  }

}
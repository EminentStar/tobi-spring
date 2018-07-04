package org.eminentstar.temp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

public class SimpleGetServletTest {
  @Test
  public void doGet() throws ServletException, IOException {
    // Given
    MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
    req.addParameter("name", "Spring");

    MockHttpServletResponse res = new MockHttpServletResponse();

    // When
    SimpleGetServlet servlet = new SimpleGetServlet();
    servlet.service(req, res);

    // Then
//    assertThat(res.getContentAsString(), is("<HTML><BODY>Hello Spring</BODY></HTML>"));
    assertThat(res.getContentAsString().contains("Hello Spring"), is(true));
  }

  /**
   * MockHttpServletRequest: 서블릿에 전달할 HttpServletRequest 타입의 HTTP 요청정보를 구성하게 해줌.
   */
  private void setMockHttpServletRequest(MockHttpServletRequest req) {
    req.setParameter("param1", "bulabula");
    req.setRemoteHost("127.0.0.1");
    req.setRemoteAddr("127.0.0.1");
    req.setAttribute("attr1", "bulabula");
    req.setCookies(null);

    // HTTP 세션 정보를 활용해야한다면 아래와 같이 목 사용 가능
    MockHttpSession session = new MockHttpSession();
    session.putValue("cart", new Object());

    req.setSession(session);
  }

  /**
   * 서블릿에서 생성되는 결과를 담음.
   */
  private void setMockHttpServletResponse(MockHttpServletResponse res) throws UnsupportedEncodingException {
    String strResult = res.getContentAsString();
    byte[] byteArrResult = res.getContentAsByteArray();
    // 그외 쿠키 세션, 콘텐트 타입 에러 메시지 등을 확인가능
  }
}

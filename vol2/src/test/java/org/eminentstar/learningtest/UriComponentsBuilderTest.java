package org.eminentstar.learningtest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class UriComponentsBuilderTest {
  String userId;
  int orderId;

  @Before
  public void setUp() {
    userId = "eminentstar";
    orderId = 122;
  }

  @Test
  public void parameterSettingTest() {
    // Given
    UriComponents uriComponents = UriComponentsBuilder.fromUriString(
      "http://www.eminentstar.org/users/{user}/orders/{order}").build();

    // When
    String uri = uriComponents.expand(userId, orderId).encode().toUriString();

    // Then
    assertThat(uri, is(String.format("http://www.eminentstar.org/users/%s/orders/%s", userId, orderId)));
  }

  @Test
  public void urlOrganizationTest() {
    // Given
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
      .scheme("http")
      .host("www.eminentstar.org")
      .path("/users/{user}/orders/{order}")
      .build();

    // When
    String uri = uriComponents.expand(userId, orderId).encode().toUriString();

    // Then
    assertThat(uri, is(String.format("http://www.eminentstar.org/users/%s/orders/%s", userId, orderId)));
  }

  @Ignore("param replace가 잘 작동하지않음. 나중에 보자.")
  @Test
  public void uriOfCurrentRequestModificationTest() throws IOException {
    // Given
    HttpServletRequest request = new MockHttpServletRequest("GET", "http://www.eminentstar.org");
    ((MockHttpServletRequest)request).setQueryString("userId=eminentstar");

    // When
    UriComponents uriComponents = ServletUriComponentsBuilder.fromRequest(request)
      .replaceQueryParam("userId", "{userId}").build()
      .expand("123")
      .encode();

    String uri = uriComponents.toUriString();

    // Then
    System.out.println(uri);

  }

}

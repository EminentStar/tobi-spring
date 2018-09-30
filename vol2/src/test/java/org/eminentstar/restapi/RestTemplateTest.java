package org.eminentstar.restapi;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

  private RestTemplate template;

  @Before
  public void setUp() {
    template = new RestTemplate();
    template.setMessageConverters(getMessageConverters());
  }

  @Test
  public void restTemplateTest() {
    // given
    String url = "https://api.github.com/users/{userId}";

    // when
    String result = template.getForObject(url, String.class, "eminentstar");

    // then
    System.out.println(result);
  }

  @Test
  public void dataBindingTest() {
    // given
    String url = "https://api.github.com/users/{userId}";

    // when
    JacksonTestModel result = template.getForObject(url, JacksonTestModel.class, "eminentstar");

    // then
    System.out.println(result);
  }

  private List getMessageConverters() {
    List<HttpMessageConverter<?>> httpMessageConverterList = new ArrayList();
    httpMessageConverterList.add(new StringHttpMessageConverter());
    httpMessageConverterList.add(new FormHttpMessageConverter());

    ObjectMapper objectMapper = new ObjectMapper();

    MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
    jsonConverter.setObjectMapper(objectMapper);
    httpMessageConverterList.add(jsonConverter);

    return httpMessageConverterList;
  }

}

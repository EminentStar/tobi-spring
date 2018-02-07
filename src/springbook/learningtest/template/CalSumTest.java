package springbook.learningtest.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

public class CalSumTest {
  @Test
  public void sumOfNumbers() throws IOException {
    // Given
    Calculator calculator = new Calculator();

    // When
    int sum = calculator.calcSum(getClass().getResource(
      "numbers.txt").getPath());

    // Then
    assertThat(sum, is(10));
  }
}

package springbook.learningtest.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CalSumTest {
  private Calculator calculator;
  private String numFilepath;

  @Before
  public void setUp() {
    this.calculator = new Calculator();
    this.numFilepath = getClass().getResource("numbers.txt").getPath();
  }

  @Test
  public void sumOfNumbers() throws IOException {
    // Given

    // When

    // Then
    assertThat(calculator.calcSum(this.numFilepath), is(10));
  }

  @Test
  public void multiplyOfNumbers() throws IOException {
    // Given

    // When

    // Then
    assertThat(calculator.calcMultiply(this.numFilepath), is(24));
  }
}

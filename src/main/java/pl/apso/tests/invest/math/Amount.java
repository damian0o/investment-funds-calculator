package pl.apso.tests.invest.math;

import lombok.Data;

/**
 * Wrapper class for hold amount of money dedicated to investments.
 * Contains basics math operations on money.
 */
@Data
public class Amount {
  public static Amount zero = new Amount(0);

  private final long value;

  public Amount minus(Amount other) {
    return new Amount(value - other.value);
  }

  public Amount add(Amount other) {
    return new Amount(value + other.value);
  }

  public Amount percentage(Percentage percentage) {
    return new Amount(value * percentage.getValue() / 10000);
  }
}

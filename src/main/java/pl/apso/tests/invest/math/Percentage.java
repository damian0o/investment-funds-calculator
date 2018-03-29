package pl.apso.tests.invest.math;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Wrapper class for percentages.
 * Contains basics operation on percentages used in solution.
 */
@EqualsAndHashCode
@ToString
public class Percentage {
  public static Percentage zero = new Percentage(0L);
  private final long value;

  public Percentage(long value) {
    this.value = value;
  }

  public Percentage divide(long divisor) {
    return new Percentage(value / divisor);
  }

  public Percentage add(Percentage points) {
    return new Percentage(value + points.value);
  }

  long getValue() {
    return value;
  }

  public String show() {
    return String.format("%s %%", value / 100);
  }

  public Percentage floorMod(int size) {
    return new Percentage(Math.floorMod(value, size));
  }
}

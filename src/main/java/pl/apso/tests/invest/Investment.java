package pl.apso.tests.invest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.apso.tests.invest.math.Percentage;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Investment {
  private Percentage percentage;
  private Fund fund;

  public Investment(long percentage, Fund fund) {
    this.percentage = new Percentage(percentage);
    this.fund = fund;
  }

}

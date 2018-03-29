package pl.apso.tests.invest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.apso.tests.invest.math.Amount;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Investments {
  private Amount amount;
  private InvestmentType investmentType;
  private List<Investment> investments;
}

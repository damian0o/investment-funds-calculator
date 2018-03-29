package pl.apso.tests.invest;

import pl.apso.tests.invest.math.Amount;
import pl.apso.tests.invest.math.Percentage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Investment calculator implementation.
 * Responsible for calculate amounts for selected investment funds.
 */
public class InvestmentCalculator {
  private InvestmentType investType;
  private Amount investAmount;

  public InvestmentCalculator(InvestmentType type, Amount amount) {
    this.investType = type;
    this.investAmount = amount;
  }

  /**
   * @param selectedFunds given funds to calculate investments amounts.
   * @return Investments
   */
  public Investments calcInvestmentAmounts(List<Fund> selectedFunds) {
    Map<FundType, List<Fund>> groups = selectedFunds.stream().collect(groupingBy(Fund::getType));
    Stream<Investment> investments = groups.entrySet().stream().flatMap(
      e -> calcInvestmentsPercentages(investType.get(e.getKey()), e.getValue()).stream());
    return new Investments(investAmount, investType, investments.collect(Collectors.toList()));
  }

  private List<Investment> calcInvestmentsPercentages(Percentage perc, List<Fund> choseFunds) {
    Percentage rem = (choseFunds.size() == 0) ? Percentage.zero : perc.floorMod(choseFunds.size());
    List<Investment> investments = choseFunds.stream()
      .map(x -> new Investment(perc.divide(choseFunds.size()), x)).collect(Collectors.toList());
    investments.stream().findFirst().ifPresent(x -> x.setPercentage(x.getPercentage().add(rem)));
    return investments;
  }
}



package pl.apso.tests.invest;

import pl.apso.tests.invest.math.Amount;

import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator {

  public Report generate(Investments investment) {
    List<ReportLine> lines = investment.getInvestments().stream()
      .map(x -> new ReportLine(
        x.getFund().getType(),
        x.getFund().getName(),
        investment.getAmount().percentage(x.getPercentage()),
        x.getPercentage()))
      .collect(Collectors.toList());

    Amount sum = lines.stream().map(ReportLine::getAmount).reduce(Amount.zero, Amount::add);

    return new Report(investment.getAmount(), investment.getAmount().minus(sum), lines);

  }

}

package pl.apso.tests.invest;

import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator {

  public Report generate(SuggestedInvestment investment) {
    List<ReportLine> lines = investment.getInvestments().stream()
      .map(x -> new ReportLine(
        x.getFund().getType(),
        x.getFund().getName(),
        x.getPercentage().eval(investment.getAmount()),
        x.getPercentage().getValue()))
      .collect(Collectors.toList());

    long sum = lines.stream().map(x -> x.getAmount()).reduce(0L, (x, y) -> x + y);

    return new Report(investment.getAmount().minus(sum), lines);

  }

}

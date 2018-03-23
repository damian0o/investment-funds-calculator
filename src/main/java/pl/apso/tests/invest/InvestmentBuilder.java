package pl.apso.tests.invest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * InvestmentBuilder implementation.
 */
public class InvestmentBuilder {
  private InvestmentType investType;
  private Amount investAmount;

  InvestmentBuilder(InvestmentType type, Amount amount) {
    this.investType = type;
    this.investAmount = amount;
  }

  SuggestedInvestment calcSuggestion(List<Fund> userFunds) {
    Map<FundType, List<Fund>> groupedByFund = new HashMap<>();
    for (Fund userFund : userFunds) {
      groupedByFund.computeIfAbsent(userFund.getType(), k -> new ArrayList<>()).add(userFund);
    }

    List<Investment> temp = groupedByFund.values().stream().flatMap(
      x -> x.stream().map(y -> new Investment(investType.get(y.getType()).divide(x.size()), y))
    ).collect(Collectors.toList());
    return new SuggestedInvestment(investAmount, investType, temp);
  }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class SuggestedInvestment {
  private Amount amount;
  private InvestmentType investmentType;
  private List<Investment> investments;
}

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

/**
 * Represent choose investment fund
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class Fund {
  private String name;
  private FundType type;
}

@Data
class Amount {
  private final Long value;

  public Amount minus(long other) {
    return new Amount(value - other);
  }
}

@EqualsAndHashCode
class Percentage {
  private final Long value;

  Percentage(Long value) {
    this.value = value;
  }

  public Long getValue() {
    return value;
  }

  Percentage divide(int divisor) {
    return new Percentage(value / divisor);
  }

  public Long eval(Amount amount) {
    return amount.getValue() * value / 100;
  }
}

/**
 * Investment investType with percentages to invest for specified investType
 */
enum InvestmentType {
  SAFE(20, 75, 5),
  BALANCED(30, 60, 10),
  AGGRESSIV(40, 20, 40);

  private final Percentage polish;
  private final Percentage foreign;
  private final Percentage money;

  InvestmentType(long polish, long foreign, long money) {
    this.polish = new Percentage(polish);
    this.foreign = new Percentage(foreign);
    this.money = new Percentage(money);
  }

  public Percentage get(FundType fundType) {
    Percentage amount = new Percentage(0L);
    switch (fundType) {
      case POLISH:
        amount = polish;
        break;
      case FOREIGN:
        amount = foreign;
        break;
      case MONEY:
        amount = money;
    }
    return amount;
  }
}

enum FundType {
  POLISH,
  FOREIGN,
  MONEY
}


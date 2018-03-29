package pl.apso.tests.invest;

import pl.apso.tests.invest.math.Percentage;

/**
 * Investment type with percentages to invest for specified type.
 * Sum of percentage should not be greater the 100%.
 */
enum InvestmentType {
  SAFE(20, 75, 5),
  BALANCED(30, 60, 10),
  AGGRESSIVE(40, 20, 40);

  private final Percentage polish;
  private final Percentage foreign;
  private final Percentage money;

  InvestmentType(long polish, long foreign, long money) {
    this.polish = new Percentage(polish * 100);
    this.foreign = new Percentage(foreign * 100);
    this.money = new Percentage(money * 100);
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

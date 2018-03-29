package pl.apso.tests.invest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.apso.tests.invest.math.Amount;
import pl.apso.tests.invest.math.Percentage;

import java.util.List;


/**
 * Represents investments suggestion report
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
  private Amount amount;
  private Amount unspent;
  private List<ReportLine> reportLines;

  public void show() {
    System.out.println("=========Report===========");
    System.out.println(String.format("Amount: %d", amount.getValue()));
    System.out.println(String.format("Not spent amount: %d", unspent.getValue()));
    for (ReportLine line : reportLines) {
      System.out.println(line);
    }
  }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ReportLine {
  private FundType type;
  private String name;
  private Amount amount;
  private Percentage percentage;

  public String toString() {
    return String.format("%15s | %25s | %10s | %10s",
      type.toString(),
      name,
      String.valueOf(amount.getValue()),
      String.valueOf(percentage.show()));
  }
}

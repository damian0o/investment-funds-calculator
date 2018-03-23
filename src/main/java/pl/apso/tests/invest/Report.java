package pl.apso.tests.invest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Represents investments suggestion report
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
  private Amount rest;
  private List<ReportLine> reportLines;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ReportLine {
  private FundType type;
  private String name;
  private long amount;
  private long percentage;
}

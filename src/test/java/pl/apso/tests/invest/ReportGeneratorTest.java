package pl.apso.tests.invest;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.apso.tests.invest.FundType.FOREIGN;
import static pl.apso.tests.invest.FundType.MONEY;
import static pl.apso.tests.invest.FundType.POLISH;
import static pl.apso.tests.invest.InvestmentType.BALANCED;
import static pl.apso.tests.invest.InvestmentType.SAFE;

public class ReportGeneratorTest {

  @Test
  public void shouldGenerateReportForEmptySuggestion() {
    // when
    ReportGenerator generator = new ReportGenerator();
    SuggestedInvestment suggestion = new SuggestedInvestment(new Amount(10000L), BALANCED, Collections.emptyList());
    Report report = generator.generate(suggestion);
    // then
    assertThat(report).isEqualTo(new Report(new Amount(10000L), Collections.emptyList()));

  }

  @Test
  public void shouldReturnRestAmount() {
    // given
    Fund fundPol1 = new Fund("1", POLISH);
    Fund fundPol2 = new Fund("2", POLISH);
    Fund fundFor1 = new Fund("1", FOREIGN);
    Fund fundFor2 = new Fund("2", FOREIGN);
    Fund fundFor3 = new Fund("3", FOREIGN);
    Fund fundMon1 = new Fund("1", MONEY);

    List<Investment> investments = Arrays.asList(new Investment(10, fundPol1),
      new Investment(10, fundPol2),
      new Investment(25, fundFor1),
      new Investment(25, fundFor2),
      new Investment(25, fundFor3),
      new Investment(5, fundMon1));
    // when
    ReportGenerator generator = new ReportGenerator();
    SuggestedInvestment suggestion = new SuggestedInvestment(new Amount(10001L), SAFE, investments);
    Report report = generator.generate(suggestion);
    // then
    assertThat(report.getRest()).isEqualTo(new Amount(1L));
  }

}
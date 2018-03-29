package pl.apso.tests.invest;

import org.junit.Test;
import pl.apso.tests.invest.math.Amount;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.apso.tests.invest.FundType.*;
import static pl.apso.tests.invest.InvestmentType.AGGRESSIVE;
import static pl.apso.tests.invest.InvestmentType.SAFE;

public class InvestmentsSolverTest {

  @Test
  public void shouldReturnEmptyWhenNoFundsProvided() {
    // given
    List<Fund> funds = Collections.emptyList();
    // when
    InvestmentCalculator solver = new InvestmentCalculator(SAFE, new Amount(10000L));
    Investments investments = solver.calcInvestmentAmounts(funds);
    // then
    assertThat(investments.getAmount()).isEqualTo(new Amount(10000L));
    assertThat(investments.getInvestmentType()).isEqualTo(SAFE);
    assertThat(investments.getInvestments()).isEmpty();
  }

  @Test
  public void shouldCalculateResultsForSingleFundFromEachType() {
    // given
    Fund fund1 = new Fund("1", POLISH);
    Fund fund2 = new Fund("1", FOREIGN);
    Fund fund3 = new Fund("1", MONEY);
    List<Fund> funds = asList(fund1, fund2, fund3);

    // when
    InvestmentCalculator solver = new InvestmentCalculator(AGGRESSIVE, new Amount(10000L));
    Investments investments = solver.calcInvestmentAmounts(funds);
    // then
    assertThat(investments.getAmount()).isEqualTo(new Amount(10000L));
    assertThat(investments.getInvestmentType()).isEqualTo(AGGRESSIVE);
    assertThat(investments.getInvestments()).containsExactlyInAnyOrder(
      new Investment(4000, fund1),
      new Investment(2000, fund2),
      new Investment(4000, fund3)
    );
  }

  @Test
  public void shouldCalculateResultsForMultiplePolish() {
    // given
    Fund fundPol1 = new Fund("1", POLISH);
    Fund fundPol2 = new Fund("1", POLISH);
    Fund fundFor1 = new Fund("2", FOREIGN);
    Fund fundMon1 = new Fund("1", MONEY);
    List<Fund> funds = asList(fundPol1, fundPol2, fundFor1, fundMon1);

    // when
    InvestmentCalculator solver = new InvestmentCalculator(AGGRESSIVE, new Amount(10000L));
    List<Investment> investments = solver.calcInvestmentAmounts(funds).getInvestments();
    // then
    assertThat(investments).containsExactlyInAnyOrder(
      new Investment(2000, fundPol1),
      new Investment(2000, fundPol2),
      new Investment(2000, fundFor1),
      new Investment(4000, fundMon1)
    );
  }

  @Test
  public void shouldCalculateResultsForMultipleFundsFromEachType() {
    // given
    Fund fundPol1 = new Fund("1", POLISH);
    Fund fundPol2 = new Fund("2", POLISH);
    Fund fundFor1 = new Fund("1", FOREIGN);
    Fund fundFor2 = new Fund("2", FOREIGN);
    Fund fundFor3 = new Fund("3", FOREIGN);
    Fund fundMon1 = new Fund("1", MONEY);
    List<Fund> funds = asList(fundPol1, fundPol2, fundFor1, fundFor2, fundFor3, fundMon1);
    // when
    List<Investment> investments = new InvestmentCalculator(SAFE, new Amount(10000L)).calcInvestmentAmounts(funds).getInvestments();
    // then
    assertThat(investments).containsExactlyInAnyOrder(
      new Investment(1000, fundPol1),
      new Investment(1000, fundPol2),
      new Investment(2500, fundFor1),
      new Investment(2500, fundFor2),
      new Investment(2500, fundFor3),
      new Investment(500, fundMon1)
    );
  }

  @Test
  public void shouldHandleNotDivisiveAmount() {
    // given
    Fund fundPol1 = new Fund("1", POLISH);
    Fund fundPol2 = new Fund("2", POLISH);
    Fund fundFor1 = new Fund("1", FOREIGN);
    Fund fundFor2 = new Fund("2", FOREIGN);
    Fund fundFor3 = new Fund("3", FOREIGN);
    Fund fundMon1 = new Fund("1", MONEY);
    List<Fund> funds = asList(fundPol1, fundPol2, fundFor1, fundFor2, fundFor3, fundMon1);
    // when
    Investments investments = new InvestmentCalculator(SAFE, new Amount(10001L)).calcInvestmentAmounts(funds);
    // then
    assertThat(investments.getInvestments()).containsExactlyInAnyOrder(
      new Investment(1000, fundPol1),
      new Investment(1000, fundPol2),
      new Investment(2500, fundFor1),
      new Investment(2500, fundFor2),
      new Investment(2500, fundFor3),
      new Investment(500, fundMon1)
    );
  }

  @Test
  public void shouldHandleSmallPercentages() {
    // given
    Fund fundPol1 = new Fund("1", POLISH);
    Fund fundPol2 = new Fund("2", POLISH);
    Fund fundPol3 = new Fund("3", POLISH);
    Fund fundFor1 = new Fund("1", FOREIGN);
    Fund fundFor2 = new Fund("2", FOREIGN);
    Fund fundMon1 = new Fund("1", MONEY);
    List<Fund> funds = asList(fundPol1, fundPol2, fundPol3, fundFor1, fundFor2, fundMon1);
    // when
    Investments investments = new InvestmentCalculator(SAFE, new Amount(10001L)).calcInvestmentAmounts(funds);
    // then
    assertThat(investments.getInvestments()).containsExactlyInAnyOrder(
      new Investment(668, fundPol1),
      new Investment(666, fundPol2),
      new Investment(666, fundPol3),
      new Investment(3750, fundFor1),
      new Investment(3750, fundFor2),
      new Investment(500, fundMon1)
    );
  }

}
package pl.apso.tests.invest;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.apso.tests.invest.FundType.*;
import static pl.apso.tests.invest.InvestmentType.AGGRESSIV;
import static pl.apso.tests.invest.InvestmentType.SAFE;

public class InvestmentsSolverTest {

  @Test
  public void shouldReturnEmptyWhenNoFundsProvided() {
    // given
    List<Fund> funds = Collections.emptyList();
    // when
    InvestmentBuilder solver = new InvestmentBuilder(SAFE, new Amount(10000L));
    List<Investment> suggestion = solver.calcSuggestion(funds).getInvestments();
    // then
    assertThat(suggestion).isEmpty();
  }

  @Test
  public void shouldCalculateResultsForSingleFundFromEachType() {
    // given
    Fund fund1 = new Fund("1", POLISH);
    Fund fund2 = new Fund("1", FOREIGN);
    Fund fund3 = new Fund("1", MONEY);
    List<Fund> funds = asList(fund1, fund2, fund3);

    // when
    InvestmentBuilder solver = new InvestmentBuilder(AGGRESSIV, new Amount(10000L));
    List<Investment> suggestion = solver.calcSuggestion(funds).getInvestments();
    // then
    assertThat(suggestion).containsExactlyInAnyOrder(
      new Investment(40, fund1),
      new Investment(20, fund2),
      new Investment(40, fund3)
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
    InvestmentBuilder solver = new InvestmentBuilder(AGGRESSIV, new Amount(10000L));
    List<Investment> suggestion = solver.calcSuggestion(funds).getInvestments();
    // then
    assertThat(suggestion).containsExactlyInAnyOrder(
      new Investment(20, fundPol1),
      new Investment(20, fundPol2),
      new Investment(20, fundFor1),
      new Investment(40, fundMon1)
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
    List<Investment> suggestion = new InvestmentBuilder(SAFE, new Amount(10000L)).calcSuggestion(funds).getInvestments();
    // then
    assertThat(suggestion).containsExactlyInAnyOrder(
      new Investment(10, fundPol1),
      new Investment(10, fundPol2),
      new Investment(25, fundFor1),
      new Investment(25, fundFor2),
      new Investment(25, fundFor3),
      new Investment(5, fundMon1)
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
    SuggestedInvestment suggestion = new InvestmentBuilder(SAFE, new Amount(10001L)).calcSuggestion(funds);
    // then
    assertThat(suggestion.getInvestments()).containsExactlyInAnyOrder(
      new Investment(10, fundPol1),
      new Investment(10, fundPol2),
      new Investment(25, fundFor1),
      new Investment(25, fundFor2),
      new Investment(25, fundFor3),
      new Investment(5, fundMon1)
    );
  }

}
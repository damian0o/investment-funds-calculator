package pl.apso.tests.invest;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.apso.tests.invest.Fund.*;
import static pl.apso.tests.invest.InvestmentType.AGGRESSIV;
import static pl.apso.tests.invest.InvestmentType.SAFE;

/**
 * Contains test cases for funds calculator
 */
public class CalculatorTest {


    @Test
    public void shouldReturnEmptyWhenNoFundsProvided() {
        // given
        List<UserFund> funds = Collections.emptyList();
        // when
        Calculator calculator = new Calculator(SAFE, new Amount(10000L));
        List<Result> results = calculator.calculate(funds);
        // then
        assertThat(results).isEmpty();
    }

    @Test
    public void shouldCalculateResultsForSingleFundFromEachType() {
        // given
        UserFund fund1 = UserFund.builder().name("1").type(POLISH).build();
        UserFund fund2 = UserFund.builder().name("1").type(FOREIGN).build();
        UserFund fund3 = UserFund.builder().name("1").type(MONEY).build();
        List<UserFund> funds = asList(fund1, fund2, fund3);

        // when
        Calculator calculator = new Calculator(AGGRESSIV, new Amount(10000L));
        List<Result> results = calculator.calculate(funds);
        // then
        assertThat(results).containsExactlyInAnyOrder(
                new Result(4000, 40, fund1),
                new Result(2000, 20, fund2),
                new Result(4000, 40, fund3)
        );
    }

    @Test
    public void shouldCalculateResultsForMultiplePolish() {
        // given
        UserFund fundPol1 = UserFund.builder().name("1").type(POLISH).build();
        UserFund fundPol2 = UserFund.builder().name("1").type(POLISH).build();
        UserFund fundFor1 = UserFund.builder().name("2").type(FOREIGN).build();
        UserFund fundMon1 = UserFund.builder().name("1").type(MONEY).build();
        List<UserFund> funds = asList(fundPol1, fundPol2, fundFor1, fundMon1);

        // when
        Calculator calculator = new Calculator(AGGRESSIV, new Amount(10000L));
        List<Result> results = calculator.calculate(funds);
        // then
        assertThat(results).containsExactlyInAnyOrder(
                new Result(2000, 20, fundPol1),
                new Result(2000, 20, fundPol2),
                new Result(2000, 20, fundFor1),
                new Result(4000, 40, fundMon1)
        );
    }

    @Test
    public void shouldCalculateResultsForMultipleFundsFromEachType() {
        // given
        UserFund fundPol1 = new UserFund(POLISH, "1");
        UserFund fundPol2 = new UserFund(POLISH, "2");
        UserFund fundFor1 = new UserFund(FOREIGN, "1");
        UserFund fundFor2 = new UserFund(FOREIGN, "2");
        UserFund fundFor3 = new UserFund(FOREIGN, "3");
        UserFund fundMon1 = new UserFund(MONEY, "1");
        List<UserFund> funds = asList(fundPol1, fundPol2, fundFor1, fundFor2, fundFor3, fundMon1);
        // when
        List<Result> results = new Calculator(SAFE, new Amount(10000L)).calculate(funds);
        // then
        assertThat(results).containsExactlyInAnyOrder(
                new Result(1000, 10, fundPol1),
                new Result(1000, 10, fundPol2),
                new Result(2500, 25, fundFor1),
                new Result(2500, 25, fundFor2),
                new Result(2500, 25, fundFor3),
                new Result(500, 5, fundMon1)
        );
    }

}
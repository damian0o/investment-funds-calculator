package pl.apso.tests.invest;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Application starting point
 */
public class App {

    public static void main(String[] main) {

        List<UserFund> chooseFunds = Arrays.asList(
                new UserFund(Fund.POLISH, "Fundusz Polski 1"),
                new UserFund(Fund.POLISH, "Fundusz Polski 2"),
                new UserFund(Fund.FOREIGN, "Fundusz Zagraniczny 1"),
                new UserFund(Fund.FOREIGN, "Fundusz Zagraniczny 2"),
                new UserFund(Fund.FOREIGN, "Fundusz Zagraniczny 3"),
                new UserFund(Fund.MONEY, "Fundusz Pieniężny 1")
        );


    }
}


/**
 * Calculator implementation.
 */
class Calculator {
    private InvestmentType investType;
    private Amount investAmount;

    Calculator(InvestmentType type, Amount amount) {
        this.investType = type;
        this.investAmount = amount;
    }

    List<Result> calculate(List<UserFund> userFunds) {
        Map<Fund, List<UserFund>> groupedByFund = new HashMap<>();
        for (UserFund userFund : userFunds) {
            groupedByFund.computeIfAbsent(userFund.getType(), k -> new ArrayList<>()).add(userFund);
        }

        return groupedByFund.values().stream().flatMap(x ->
                x.stream()
                        .map(y -> {
                            Percentage res = investType.get(y.getType()).divide(x.size());
                            return new Result(new Amount(res.eval(investAmount)), res, y);
                        })
        ).collect(Collectors.toList());

    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Result {
    private Amount amount;
    private Percentage percentage;
    private UserFund fund;

    public Result(long amount, long percentage, UserFund fund) {
        this.amount = new Amount(amount);
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
@Builder
class UserFund {
    private Fund type;
    private String name;
}

@Data
class Amount {
    private final Long value;
}

@EqualsAndHashCode
class Percentage {
    private final Long value;

    Percentage(Long value) {
        this.value = value;
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

    public Percentage get(Fund fund) {
        Percentage amount = new Percentage(0L);
        switch (fund) {
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

enum Fund {
    POLISH,
    FOREIGN,
    MONEY
}



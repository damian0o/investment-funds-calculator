package pl.apso.tests.invest;

import pl.apso.tests.invest.math.Amount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.apso.tests.invest.InvestmentType.SAFE;

/**
 * Parse csv files with selected investment funds and print to console suggested amount 
 */
public class App {
  public static void main(String[] args) {
    try {
      List<Fund> funds = parseExampleFile("example1.csv");
      InvestmentCalculator calc = new InvestmentCalculator(SAFE, new Amount(10000L));
      Investments investments = calc.calcInvestmentAmounts(funds);
      Report report = new ReportGenerator().generate(investments);
      report.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static List<Fund> parseExampleFile(String exampleFileName) throws IOException {
    Stream<String> lines = Files
      .lines(Paths.get(
        Objects.requireNonNull(App.class.getClassLoader().getResource(exampleFileName)).getFile()))
      .skip(1);
    return lines.map(line -> Funds.parse(line.split(","))).collect(Collectors.toList());
  }
}

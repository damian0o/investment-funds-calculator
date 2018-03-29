package pl.apso.tests.invest;

/**
 * Util class for Fund
 */
public class Funds {
  public static Fund parse(String[] vals) {
    return new Fund(vals[2], FundType.parse(vals[1]));
  }
}

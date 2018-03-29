package pl.apso.tests.invest;

import jdk.nashorn.internal.runtime.ParserException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Represent one of three investment fund type.
 * Contains method responsible for serialization and deserialization of class for simplicity.
 */
public enum FundType {
  POLISH,
  FOREIGN,
  MONEY;

  public static FundType parse(String val) {
    switch (val) {
      case "Polskie":
        return POLISH;
      case "Zagraniczne":
        return FOREIGN;
      case "Pieniężne":
        return MONEY;
    }
    throw new ParserException(String.format("Could not parse to FundType [val='%s']", val));
  }

  @Override
  public String toString() {
    switch (this) {
      case POLISH:
        return "Polskie";
      case FOREIGN:
        return "Zagraniczne";
      case MONEY:
        return "Pienieżne";
    }
    throw new NotImplementedException();
  }
}

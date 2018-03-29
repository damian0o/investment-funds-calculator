package pl.apso.tests.invest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent raw from selected investment funds table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fund {
  private String name;
  private FundType type;
}

package main.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SettingPatternsDto {

  private String pattern;

  private Long time;
  private Integer count;
  private BigDecimal deltaMaxAsk;
  private BigDecimal deltaMinAsk;
  private BigDecimal deltaMaxBid;
  private BigDecimal deltaMinBid;

  private Integer countFirst;
  private Long timeFirst;
  private Integer countSecond;
  private Long timeSecond;

}

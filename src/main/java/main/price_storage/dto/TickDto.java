package main.price_storage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickDto {

  private BigDecimal ask;
  private BigDecimal bid;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
  private Date time;
  private Long timeMsc;
  private BigDecimal last;
  private Long volume;
  private Double volumeReal;
  private Integer flags;



}

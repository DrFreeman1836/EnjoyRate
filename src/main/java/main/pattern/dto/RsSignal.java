package main.pattern.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import main.pattern.enam.TypeTrend;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RsSignal {

  List<Signal> listSignal = new ArrayList<>();

  public void addSignal(BigDecimal price, String type, int pattern, TypeTrend trend) {
    listSignal.add(new Signal(price, type, pattern, trend));
  }

  public List<Signal> getSignals() {
    return listSignal;
  }

  public record Signal(BigDecimal price, String type, int pattern, TypeTrend trend){}

}

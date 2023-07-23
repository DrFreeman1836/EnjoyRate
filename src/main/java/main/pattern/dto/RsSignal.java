package main.pattern.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import main.pattern.enam.TypeTrend;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RsSignal {

  List<Signal> listSignal = new ArrayList<>();

  public void addSignal(String type, int pattern, TypeTrend trend) {
    listSignal.add(new Signal(type, pattern, trend));
  }

  public record Signal(String type, int pattern, TypeTrend trend){}

}

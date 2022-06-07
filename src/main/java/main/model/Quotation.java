package main.model;

import java.util.HashMap;
import lombok.Data;

@Data
public class Quotation {

  private long timestamp;

  private String base;

  private HashMap<String, Double> rates;

}

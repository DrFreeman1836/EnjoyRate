package main.pattern.service;

import java.util.HashMap;
import java.util.Map;

public interface PatternPrice {

  void setParams(HashMap<String, Number> params);

  int getResponse();

  HashMap<String, Number> getParams();

}

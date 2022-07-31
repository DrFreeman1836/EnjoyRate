package main.service;

import java.util.HashMap;

public interface PatternPrice {

  void initParams(HashMap<String, Number> params);

  int getResponse();

}

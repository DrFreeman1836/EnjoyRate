package main.service;

import java.util.HashMap;

public class AbstractPatternPassivity implements PatternPrice{

  @Override
  public void initParams(HashMap<String, Number> params) {

  }

  @Override
  public int getResponse() {
    return 0;
  }
}

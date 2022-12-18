package main.service.impl.passivity;

import java.util.HashMap;
import main.service.AbstractPatternPassivity;
import main.storage.impl.TickManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Passivity extends AbstractPatternPassivity {


  @Autowired
  public Passivity(TickManagerServiceImpl tickManagerService) {
    super(tickManagerService);
  }

  @Override
  public void setParams(HashMap<String, Number> params) {
    super.setParams(params);
  }

  @Override
  public int getResponse() {
    return super.getResponse();
  }

  @Override
  public HashMap<String, Number> getParams() {
    return super.getParams();
  }
}

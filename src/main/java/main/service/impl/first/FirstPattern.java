package main.service.impl.first;

import java.util.HashMap;
import main.service.AbstractPatternActivity;
import main.storage.impl.TickManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirstPattern extends AbstractPatternActivity {

  @Autowired
  public FirstPattern(TickManagerServiceImpl tickManagerService) {
    super(tickManagerService);
  }

  @Override
  public int getResponse() {
    return super.getResponse();
  }

  @Override
  public void initParams(HashMap<String, Number> params) {
    super.initParams(params);
  }
}

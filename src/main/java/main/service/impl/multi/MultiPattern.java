package main.service.impl.multi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import main.enam.TypeSignalMulti;
import main.service.AbstractPatternActivity;
import main.storage.impl.TickManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiPattern extends AbstractPatternActivity {

  private Integer LOW_LEVEL = 15;
  private Integer MIDDLE_LEVEL = 20;
  private Integer HIGH_LEVEL = 25;
  private final HashSet<Integer> checkResponse = new HashSet<Integer>(Set.of(200, 201, 202));

  @Autowired
  public MultiPattern(TickManagerServiceImpl tickManagerService) {
    super(tickManagerService);
  }

  @Override
  public int getResponse() {
    setLevelSettings(HIGH_LEVEL);
    if (checkResponse.contains(super.getResponse())) {
      return TypeSignalMulti.HIGH_LEVEL.getResponseCode();
    }
    setLevelSettings(MIDDLE_LEVEL);
    if (checkResponse.contains(super.getResponse())) {
      return TypeSignalMulti.MIDDLE_LEVEL.getResponseCode();
    }
    setLevelSettings(LOW_LEVEL);
    if (checkResponse.contains(super.getResponse())) {
      return TypeSignalMulti.LOW_LEVEL.getResponseCode();
    }

    return TypeSignalMulti.NO_PATTERN.getResponseCode();
  }

  private void setLevelSettings(Integer level) {
    super.setParams(new HashMap<>(Map.of(
        "time", getParams().get("time"),
        "count", level,
        "deltaMaxAsk", getParams().get("deltaMaxAsk"),
        "deltaMinAsk", getParams().get("deltaMinAsk"),
        "deltaMaxBid", getParams().get("deltaMaxBid"),
        "deltaMinBid", getParams().get("deltaMinBid")
    )));
  }

  @Override
  public void setParams(HashMap<String, Number> params) {
    this.HIGH_LEVEL = (Integer) params.get("highLevel");
    this.MIDDLE_LEVEL = (Integer) params.get("middleLevel");
    this.LOW_LEVEL = (Integer) params.get("lowLevel");
    super.setParams(params);
  }

  @Override
  public HashMap<String, Number> getParams() {
    HashMap<String, Number> params = new HashMap<>(super.getParams());
    params.put("highLevel", HIGH_LEVEL);
    params.put("middleLevel", MIDDLE_LEVEL);
    params.put("lowLevel", LOW_LEVEL);
    return params;
  }

}

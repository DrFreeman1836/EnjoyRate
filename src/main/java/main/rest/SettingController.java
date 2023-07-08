package main.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import main.dto.SettingDto;
import main.dto.SettingPatternsDto;
import main.service.PatternPrice;
import main.storage.ManagerTicks;
import main.storage.impl.TickManagerServiceImpl;
import main.telegram.impl.TelegramBotMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ea")
public class SettingController {

  private PatternPrice activityPattern;

  private PatternPrice passivityPattern;

  private PatternPrice multiPattern;


  @Autowired
  @Qualifier("multiPattern")
  public void setMultiPattern(PatternPrice multiPattern) {
    this.multiPattern = multiPattern;
  }

  @Autowired
  @Qualifier("passivityPattern")
  public void setPassivityPattern(PatternPrice passivityPattern) {
    this.passivityPattern = passivityPattern;
  }

  @Autowired
  @Qualifier("activityPattern")
  public void setActivityPattern(PatternPrice activityPattern) {
    this.activityPattern = activityPattern;
  }

  @PutMapping("/params")
  public ResponseEntity<?> setParams(@RequestBody SettingPatternsDto setting) {
    System.out.println(setting);
    try {
      if (setting.getPattern().equals("activity")) {
        activityPattern.setParams(new HashMap<>(Map.of(
            "time", setting.getTime() == null ? activityPattern.getParams().get("time") : setting.getTime(),
            "count", setting.getCount() == null ? activityPattern.getParams().get("count") : setting.getCount(),
            "deltaMaxAsk", setting.getDeltaMaxAsk() == null ? activityPattern.getParams().get("deltaMaxAsk") : setting.getDeltaMaxAsk(),
            "deltaMinAsk", setting.getDeltaMinAsk() == null ? activityPattern.getParams().get("deltaMinAsk") : setting.getDeltaMinAsk(),
            "deltaMaxBid", setting.getDeltaMaxBid() == null ? activityPattern.getParams().get("deltaMaxBid") : setting.getDeltaMaxBid(),
            "deltaMinBid", setting.getDeltaMinBid() == null ? activityPattern.getParams().get("deltaMinBid") : setting.getDeltaMinBid())));

        multiPattern.setParams(new HashMap<>(Map.of(
            "highLevel", setting.getHighLevel() == null ? multiPattern.getParams().get("highLevel") : setting.getHighLevel(),
            "middleLevel", setting.getMiddleLevel() == null ? multiPattern.getParams().get("middleLevel") : setting.getMiddleLevel(),
            "lowLevel", setting.getLowLevel() == null ? multiPattern.getParams().get("lowLevel") : setting.getLowLevel(),
            "time", setting.getTime() == null ? multiPattern.getParams().get("time") : setting.getTime(),
            "count", setting.getCount() == null ? multiPattern.getParams().get("count") : setting.getCount(),
            "deltaMaxAsk", setting.getDeltaMaxAsk() == null ? multiPattern.getParams().get("deltaMaxAsk") : setting.getDeltaMaxAsk(),
            "deltaMinAsk", setting.getDeltaMinAsk() == null ? multiPattern.getParams().get("deltaMinAsk") : setting.getDeltaMinAsk(),
            "deltaMaxBid", setting.getDeltaMaxBid() == null ? multiPattern.getParams().get("deltaMaxBid") : setting.getDeltaMaxBid(),
            "deltaMinBid", setting.getDeltaMinBid() == null ? multiPattern.getParams().get("deltaMinBid") : setting.getDeltaMinBid())));
        return ResponseEntity.ok("Настройки установлены");
      }

      if (setting.getPattern().equals("passivity")) {
        passivityPattern.setParams(new HashMap<>(Map.of(
            "countFirst", setting.getCountFirst() == null ? passivityPattern.getParams().get("countFirst") : setting.getCountFirst(),
            "timeFirst", setting.getTimeFirst() == null ? passivityPattern.getParams().get("timeFirst") : setting.getTimeFirst(),
            "countSecond", setting.getCountSecond() == null ? passivityPattern.getParams().get("countSecond") : setting.getCountSecond(),
            "timeSecond", setting.getTimeSecond() == null ? passivityPattern.getParams().get("timeSecond") : setting.getTimeSecond()
        )));
        return ResponseEntity.ok("Настройки установлены");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(404).body("Настройки не установлены");
    }
    return ResponseEntity.status(400).body("Передано неверное имя паттерна");
  }

  @GetMapping("/params")
  public List<SettingDto> getParams() {
    List<SettingDto> list = new ArrayList<>();
    for(Entry<String, Number> set : activityPattern.getParams().entrySet()) {
      list.add(SettingDto.builder().pattern("activity").name(set.getKey()).value(set.getValue()).build());
    }
    list.add(SettingDto.builder().pattern("activity").name("highLevel").value(multiPattern.getParams()
        .get("highLevel")).build());
    list.add(SettingDto.builder().pattern("activity").name("middleLevel").value(multiPattern.getParams()
        .get("middleLevel")).build());
    list.add(SettingDto.builder().pattern("activity").name("lowLevel").value(multiPattern.getParams()
        .get("lowLevel")).build());
    for(Entry<String, Number> set : passivityPattern.getParams().entrySet()) {
      list.add(SettingDto.builder().pattern("passivity").name(set.getKey()).value(set.getValue()).build());
    }
    return list;
  }


}

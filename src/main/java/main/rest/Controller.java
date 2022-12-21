package main.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import main.service.PatternPrice;
import main.storage.ManagerTicks;
import main.telegram.impl.TelegramBotMessages;
import main.storage.impl.TickManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ea")
public class Controller {

  private ManagerTicks tickManagerService;

  private PatternPrice activityPattern;

  private PatternPrice passivityPattern;

  private TelegramBotMessages bot;

  @Autowired
  @Qualifier("tickManagerServiceImpl")
  public void setTickManagerService(TickManagerServiceImpl tickManagerService) {
    this.tickManagerService = tickManagerService;
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

  @Autowired
  public void setBot(TelegramBotMessages bot) {
    this.bot = bot;
  }

  @PutMapping("/params")
  public ResponseEntity<?> setParams(
      @RequestParam(name = "pattern") String pattern,
      @RequestParam(name = "time", required = false) Long time,
      @RequestParam(name = "count", required = false) Integer count,
      @RequestParam(name = "deltaMaxAsk", required = false) BigDecimal deltaMaxAsk,
      @RequestParam(name = "deltaMinAsk", required = false) BigDecimal deltaMinAsk,
      @RequestParam(name = "deltaMaxBid", required = false) BigDecimal deltaMaxBid,
      @RequestParam(name = "deltaMinBid", required = false) BigDecimal deltaMinBid,

      @RequestParam(name = "countFirst", required = false) Integer countFirst,
      @RequestParam(name = "timeFirst", required = false) Long timeFirst,
      @RequestParam(name = "countSecond", required = false) Integer countSecond,
      @RequestParam(name = "timeSecond", required = false) Long timeSecond) {
    try {
      if (pattern.equals("activity")) {
        activityPattern.setParams(new HashMap<>(Map.of(
            "time", time == null ? activityPattern.getParams().get("time") : time,
            "count", count == null ? activityPattern.getParams().get("count") : count,
            "deltaMaxAsk", deltaMaxAsk == null ? activityPattern.getParams().get("deltaMaxAsk") : deltaMaxAsk,
            "deltaMinAsk", deltaMinAsk == null ? activityPattern.getParams().get("deltaMinAsk") : deltaMinAsk,
            "deltaMaxBid", deltaMaxBid == null ? activityPattern.getParams().get("deltaMaxBid") : deltaMaxBid,
            "deltaMinBid", deltaMinBid == null ? activityPattern.getParams().get("deltaMinBid") : deltaMinBid)));
        return ResponseEntity.ok("Настройки не установлены");
      }

      if (pattern.equals("passivity")) {
        passivityPattern.setParams(new HashMap<>(Map.of(
            "countFirst", countFirst,
            "timeFirst", timeFirst,
            "countSecond", countSecond,
            "timeSecond", timeSecond
        )));
        return ResponseEntity.ok("Настройки не установлены");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(404).body("Настройки не установлены");
    }
    return ResponseEntity.status(400).body("Передано неверное имя паттерна");
  }

  @GetMapping("/params")
  public ResponseEntity<?> getParams(@RequestParam(name = "pattern") String pattern) {
    if (pattern.equals("activity")) {
      return ResponseEntity.ok(activityPattern.getParams());
    }
    if (pattern.equals("passivity")) {
      return ResponseEntity.ok(passivityPattern.getParams());
    }
    return ResponseEntity.status(400).body("Передано неверное имя паттерна");
  }

  @PostMapping("/signal")
  public ResponseEntity<?> addTick(
      @RequestParam(name = "priceAsk") BigDecimal priceAsk,
      @RequestParam(name = "priceBid") BigDecimal priceBid,
      @RequestParam(name = "pattern") String pattern) {

    try {
      tickManagerService.processingTick(priceAsk, priceBid, System.currentTimeMillis());

      if (pattern.equals("activity")) {
        int res = activityPattern.getResponse();
        if (res != 404) bot.sendMessage(String.valueOf(res) + " новый активный");
        return ResponseEntity.status(res).build();
      }
      if (pattern.equals("passivity")) {
        int res = activityPattern.getResponse();
        if (res != 404) bot.sendMessage(String.valueOf(res) + " новый пассивный");
        return ResponseEntity.status(res).build();
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      bot.sendMessage(ex.toString());
      return ResponseEntity.status(500).build();
    }
    return ResponseEntity.status(405).build();
  }

}

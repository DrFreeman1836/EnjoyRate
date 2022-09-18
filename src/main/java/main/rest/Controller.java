package main.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import main.service.PatternPrice;
import main.service.impl.TelegramBotMessages;
import main.service.impl.TickManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ea")
public class Controller {

  private TickManagerServiceImpl tickManagerService;

  private PatternPrice patternPrice;

  private TelegramBotMessages bot;

  @Autowired
  public void setTickManagerService(TickManagerServiceImpl tickManagerService) {
    this.tickManagerService = tickManagerService;
  }

  @Autowired
  @Qualifier("patternDeltaPrice")
  public void setPatternPrice(PatternPrice patternPrice) {
    this.patternPrice = patternPrice;
  }

  @Autowired
  public void setBot(TelegramBotMessages bot) {
    this.bot = bot;
  }

  @PostMapping()
  public ResponseEntity<?> addTick(
      @RequestParam(name = "priceAsk") BigDecimal priceAsk,
      @RequestParam(name = "priceBid") BigDecimal priceBid) {

    Long time = System.currentTimeMillis();
    try {
      tickManagerService.processingTick(priceAsk, priceBid, time);
      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(500).build();
    }

  }

  @GetMapping("/signal")
  public ResponseEntity<?> getSignal(
      @RequestParam(name = "time") long time,
      @RequestParam(name = "count") int count,
      @RequestParam(name = "minDeltaTrend") BigDecimal minDeltaTrend,
      @RequestParam(name = "deltaMaxAsk") BigDecimal deltaMaxAsk,
      @RequestParam(name = "deltaMinAsk") BigDecimal deltaMinAsk,
      @RequestParam(name = "deltaMaxBid") BigDecimal deltaMaxBid,
      @RequestParam(name = "deltaMinBid") BigDecimal deltaMinBid) {

    try {
      patternPrice.initParams(new HashMap<>(Map.of(
          "time", time,
          "count", count,
          "minDeltaTrend", minDeltaTrend,
          "deltaMaxAsk", deltaMaxAsk,
          "deltaMinAsk", deltaMinAsk,
          "deltaMaxBid", deltaMaxBid,
          "deltaMinBid", deltaMinBid)));
      int res = patternPrice.getResponse();
      if(res != 404) bot.sendMessage(String.valueOf(res));
      return ResponseEntity.status(res).build();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(500).build();
    }

  }

  @GetMapping("/test")
  public ResponseEntity<?> test(){
    tickManagerService.getListTicks().forEach(System.out::println);
    return ResponseEntity.ok().build();
  }

}

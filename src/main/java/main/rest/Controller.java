package main.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import main.dto.RsSignal;
import main.dto.RsSignal.Signal;
import main.enam.TypeTrend;
import main.service.PatternPrice;
import main.storage.ManagerTicks;
import main.telegram.impl.TelegramBotMessages;
import main.storage.impl.TickManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ea")
public class Controller {

  private ManagerTicks tickManagerService;

  private PatternPrice activityPattern;

  private PatternPrice passivityPattern;

  private PatternPrice multiPattern;

  private TelegramBotMessages bot;

  @Autowired
  @Qualifier("tickManagerServiceImpl")
  public void setTickManagerService(TickManagerServiceImpl tickManagerService) {
    this.tickManagerService = tickManagerService;
  }

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

  @Autowired
  public void setBot(TelegramBotMessages bot) {
    this.bot = bot;
  }

  @PostMapping("/signal")
  public ResponseEntity<RsSignal> addTickk(
      @RequestParam(name = "priceAsk") BigDecimal priceAsk,
      @RequestParam(name = "priceBid") BigDecimal priceBid,
      @RequestParam(name = "activity") Boolean activity,
      @RequestParam(name = "passivity") Boolean passivity,
      @RequestParam(name = "multi") Boolean multi) {

    RsSignal rsSignal = new RsSignal();
    try {
      tickManagerService.processingTick(priceAsk, priceBid, System.currentTimeMillis());

      if (activity) {
        int res = activityPattern.getResponse();
         rsSignal.addSignal("activity", res, TypeTrend.NO_TREND);
      }
      if (passivity) {
        int res = passivityPattern.getResponse();
        rsSignal.addSignal("passivity", res, TypeTrend.NO_TREND);
      }
      if (multi) {
        int res = multiPattern.getResponse();
        rsSignal.addSignal("multi", res, TypeTrend.NO_TREND);
      }

    } catch (Exception ex) {
      //bot.sendMessage(ex.toString());
      return ResponseEntity.status(500).build();
    }
    return ResponseEntity.status(200).body(rsSignal);
  }

}

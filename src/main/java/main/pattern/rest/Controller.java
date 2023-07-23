package main.pattern.rest;

import lombok.RequiredArgsConstructor;
import main.pattern.dto.RsSignal;
import main.pattern.enam.TypeSignalPassivity;
import main.pattern.enam.TypeTrend;
import main.pattern.service.PatternPrice;
import main.notification.impl.TelegramBotMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ea")
@RequiredArgsConstructor
public class Controller {

  @Qualifier("activityPattern")
  private final PatternPrice activityPattern;

  @Qualifier("passivityPattern")
  private final PatternPrice passivityPattern;

  @Qualifier("multiPattern")
  private final PatternPrice multiPattern;

  private final TelegramBotMessages bot;

  private final Logger logger = LoggerFactory.getLogger(Controller.class);

  enum Pattern {
    ACTIVITY("activity"), PASSIVITY("passivity"), MULTI("multi");
    private final String type;
    Pattern (String type) {
      this.type = type;
    }
    String getPattern(){
      return type;
    }
  }


  @PostMapping("/signal")
  public ResponseEntity<RsSignal> addTick(
      @RequestParam(name = "activity") Boolean activity,
      @RequestParam(name = "passivity") Boolean passivity,
      @RequestParam(name = "multi") Boolean multi) {

    RsSignal rsSignal = new RsSignal();
    StringBuilder sbLog = new StringBuilder();
    if (activity) {
      int res = activityPattern.getResponse();
      if (checkResponse(res)) {
        buildResponse(rsSignal, sbLog, Pattern.ACTIVITY.getPattern(), res, TypeTrend.NO_TREND);
      }
    }
    if (passivity) {
      int res = passivityPattern.getResponse();
      if (checkResponse(res)) {
        buildResponse(rsSignal, sbLog, Pattern.PASSIVITY.getPattern(), res, TypeTrend.NO_TREND);
      }
    }
    if (multi) {
      int res = multiPattern.getResponse();
      if (checkResponse(res)) {
        buildResponse(rsSignal, sbLog, Pattern.MULTI.getPattern(), res, TypeTrend.NO_TREND);
      }
    }

    if (!sbLog.isEmpty()) {
      logger.info(sbLog.toString());
      bot.sendMessage(sbLog.toString());
    }
    return ResponseEntity.status(200).body(rsSignal);
  }

  private boolean checkResponse(int res) {
    return res != TypeSignalPassivity.NO_PATTERN.getResponseCode() && res != TypeSignalPassivity.ERROR.getResponseCode();
  }

  private void buildResponse(RsSignal rsSignal, StringBuilder sb, String pattern, int res, TypeTrend trend) {
    rsSignal.addSignal(pattern, res, trend);
    sb.append(String.format("%s: %s trend: %s", pattern, res, trend)).append("\n");
  }

}

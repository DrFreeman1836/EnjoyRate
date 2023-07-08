package main.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import main.dto.SettingDto;
import main.dto.SettingPatternsDto;
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
import org.springframework.web.bind.annotation.RequestBody;
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
        int res = passivityPattern.getResponse();
        if (res != 404) bot.sendMessage(String.valueOf(res) + " новый пассивный");
        return ResponseEntity.status(res).build();
      }
      if (pattern.equals("multi")) {
        int res = multiPattern.getResponse();
        if (res != 404) bot.sendMessage(String.valueOf(res) + " новый мульти");
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

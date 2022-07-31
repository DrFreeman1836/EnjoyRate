package main.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import main.service.PatternPrice;
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

  @Autowired
  public void setTickManagerService(TickManagerServiceImpl tickManagerService) {
    this.tickManagerService = tickManagerService;
  }

  @Autowired
  @Qualifier("patternDeltaPrice")
  public void setPatternPrice(PatternPrice patternPrice) {
    this.patternPrice = patternPrice;
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
      @RequestParam(name = "time") int time,
      @RequestParam(name = "count") int count,
      @RequestParam(name = "deltaAsk") BigDecimal deltaAsk,
      @RequestParam(name = "deltaBid") BigDecimal deltaBid) {

    try {
      patternPrice.initParams(new HashMap<>(Map.of(
          "time", time,
          "count", count,
          "deltaAsk", deltaAsk,
          "deltaBid", deltaBid)));
      int res = patternPrice.getResponse();
      return ResponseEntity.status(res).build();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(500).build();
    }

  }

}

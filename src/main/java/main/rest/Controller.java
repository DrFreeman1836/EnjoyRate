package main.rest;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import main.service.PatternDeltaPrice;
import main.service.impl.TickManagerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ea")
@RequiredArgsConstructor
public class Controller {

  private TickManagerServiceImpl tickManagerService;

  private PatternDeltaPrice patternDeltaPrice;

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
      @RequestParam(name = "time", required = false) int time,//подумать
      @RequestParam(name = "count") int count,
      @RequestParam(name = "deltaAsk") BigDecimal deltaAsk,
      @RequestParam(name = "deltaBid") BigDecimal deltaBid) {

    try {
      int res = patternDeltaPrice.getResponse();
      return ResponseEntity.status(res).build();
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(500).build();
    }

  }

}

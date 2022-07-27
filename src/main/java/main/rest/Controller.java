package main.rest;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import main.service.impl.TickManagerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ea")
@RequiredArgsConstructor
public class Controller {

  private TickManagerServiceImpl tickManagerService;

  @PostMapping()
  public ResponseEntity<?> addTick(
      @RequestParam(name = "priceAsk") BigDecimal priceAsk,
      @RequestParam(name = "priceBid") BigDecimal priceBid,
      @RequestParam(name = "flag", required = false, defaultValue = "false") Boolean flag) {

    Long time = System.currentTimeMillis();

    try {

      tickManagerService.processingTick(priceAsk, priceBid, time, flag);
      return ResponseEntity.ok().build();

    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(500).build();
    }

  }

}

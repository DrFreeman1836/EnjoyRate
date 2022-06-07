package main.rest;

import java.util.Map;
import lombok.AllArgsConstructor;
import main.service.ManagerApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class Controller {

  private final ManagerApplication manager;

  @GetMapping("/giphy")
  public ResponseEntity<?> getGiphy() {

    try {

      if (manager.getRateDifference() > 0) {
        String url = manager.getGiphy("rich");
        return ResponseEntity.ok().body(Map.of("url", url));
      } else {
        String url = manager.getGiphy("broke");
        return ResponseEntity.ok().body(Map.of("url", url));
      }

    } catch (Exception ex) {
      return ResponseEntity.status(500).body(Map.of("error", ex.toString()));
    }

  }

}
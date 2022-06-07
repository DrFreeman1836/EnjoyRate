package main.service;

import main.model.DataGiphy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "giphy-api", url = "${giphy.url}")
public interface ClientGiphy {

  @GetMapping("/search")
  DataGiphy getListGiphy(@RequestParam("api_key") String api_key, @RequestParam("q") String q);

}

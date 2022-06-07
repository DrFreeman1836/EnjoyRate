package main.service;

import main.model.Quotation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "openexchangerates-api", url = "${urlopenexchangerates.url}")
public interface ClientOpenExchangeRates {

  @GetMapping("/latest.json")
  Quotation getQuotation(@RequestParam("app_id") String app_id, @RequestParam("symbols") String symbols);

  @GetMapping(value = "/historical/{date}.json")
  Quotation getQuotationHistorical(@PathVariable("date") String date, @RequestParam("app_id") String app_id, @RequestParam("symbols") String symbols);

}

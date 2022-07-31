package main.service.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import main.enamRes.SignalByDelta;
import main.model.Tick;
import main.service.PatternPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatternDeltaPrice implements PatternPrice {

  private int time;//ms

  private int count;

  private BigDecimal deltaAsk;

  private BigDecimal deltaBid;

  private List<Tick> listTicks;

  private long minTime;

  private long maxTime;

  private BigDecimal maxPriceAsk;

  private BigDecimal minPriceAsk;

  private BigDecimal maxPriceBid;

  private BigDecimal minPriceBid;

  private TickManagerServiceImpl tickManagerService;

  @Autowired
  public void setTickManagerService(TickManagerServiceImpl tickManagerService) {
    this.tickManagerService = tickManagerService;
  }

  public void initParams(HashMap<String, Number> params) {
    this.time = params.get("time").intValue();
    this.count = params.get("count").intValue();
    this.deltaAsk = new BigDecimal(params.get("deltaAsk").toString());
    this.deltaBid = new BigDecimal(params.get("deltaBid").toString());
  }

  public int getResponse() {
    getSelection();
    if (checkPatternAll()) {
      return SignalByDelta.ALL.getResponseCode();
    }
    if (checkPatternAsk()) {
      return SignalByDelta.ASK.getResponseCode();
    }
    if (checkPatternBid()) {
      return SignalByDelta.BID.getResponseCode();
    }
    return SignalByDelta.NO_PATTERN.getResponseCode();
  }

  private void getSelection() {
    listTicks = tickManagerService.getListTicks(count);
    maxPriceAsk = listTicks.stream()
        .max(Comparator.comparing(Tick::getPriceAsk))
        .get().getPriceAsk();
    minPriceAsk = listTicks.stream()
        .min(Comparator.comparing(Tick::getPriceAsk))
        .get().getPriceAsk();
    maxPriceBid = listTicks.stream()
        .max(Comparator.comparing(Tick::getPriceBid))
        .get().getPriceBid();
    minPriceBid = listTicks.stream()
        .min(Comparator.comparing(Tick::getPriceBid))
        .get().getPriceBid();
    maxTime = listTicks.stream()
        .max(Comparator.comparing(Tick::getTimestamp))
        .get().getTimestamp();
    minTime = listTicks.stream()
        .min(Comparator.comparing(Tick::getTimestamp))
        .get().getTimestamp();
  }

  private boolean checkPatternAsk() {
    if (maxPriceAsk.subtract(minPriceAsk).compareTo(deltaAsk) <= 0
        && maxTime - minTime < time) {
      return true;
    }
    return false;
  }

  private boolean checkPatternBid() {
    if (maxPriceBid.subtract(minPriceBid).compareTo(deltaBid) <= 0
        && maxTime - minTime < time) {
      return true;
    }
    return false;
  }

  private boolean checkPatternAll() {
    if (checkPatternAsk() && checkPatternBid()) {
      return true;
    }
    return false;
  }


}

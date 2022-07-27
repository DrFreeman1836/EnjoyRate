package main.service.impl;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.model.Tick;
import main.repository.TickRepository;
import main.service.ManagerTicks;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TickManagerServiceImpl implements ManagerTicks {

  private final TickRepository tickRepository;

  private Deque<Tick> listTicks = new ArrayDeque<>();

  private final int SIZE_LIST_TICKS = 300;

  public void processingTick(BigDecimal priceAsk, BigDecimal priceBid, Long time, Boolean flag) throws Exception {

    Tick tick = Tick.builder()
        .priceAsk(priceAsk)
        .priceBid(priceBid)
        .timestamp(time)
        .flagFrog(flag)
        .build();

    if (listTicks.size() >= SIZE_LIST_TICKS) {
      listTicks.pollLast();
      listTicks.addFirst(tick);
    } else {
      listTicks.addFirst(tick);
    }

  }

  public List<Tick> getSortedListTicks() {
    return listTicks.stream().sorted(Comparator.comparingLong(Tick::getTimestamp))
        .collect(Collectors.toList());
  }

  public BigDecimal getSizeTick(BigDecimal priceAsk, BigDecimal priceBid) {
    return priceAsk.subtract(priceBid);
  }

  public long countTicks() {
    return tickRepository.count();
  }

  @Override
  public int addTick(Tick tick) {
    return tickRepository.save(tick).getId();
  }

  @Override
  public void addAllTick(List<Tick> listTicks) {
    tickRepository.saveAll(listTicks);
  }

  @Override
  public Tick getLastTick() {
    return tickRepository.findAll().stream().max(Comparator.comparingLong(Tick::getTimestamp)).get();
  }


}

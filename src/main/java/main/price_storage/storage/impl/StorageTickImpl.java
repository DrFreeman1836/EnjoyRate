package main.price_storage.storage.impl;

import java.util.*;
import java.util.stream.Collectors;
import main.price_storage.dto.TickDto;
import main.price_storage.model.Tick;
import main.price_storage.storage.StorageTick;
import org.springframework.stereotype.Service;

@Service
public class StorageTickImpl implements StorageTick {

  private Deque<Tick> listTicks = new ArrayDeque<>();

  private final int SIZE_LIST_TICKS = 5000;

  public void processingTick(TickDto tickDto, Long timestamp) throws Exception {

    Tick tick = Tick.builder()
        .ask(tickDto.getAsk())
        .bid(tickDto.getBid())
        .timestamp(timestamp)
        .time(tickDto.getTime())
        .timeMsc(tickDto.getTimeMsc())
        .flags(tickDto.getFlags())
        .last(tickDto.getLast())
        .volume(tickDto.getVolume())
        .volumeReal(tickDto.getVolumeReal())
        .build();

    if (listTicks.size() >= SIZE_LIST_TICKS) {
      listTicks.pollFirst();
    }
    listTicks.addLast(tick);

  }

  public int sizeStorageTicks() {
    return listTicks.size();
  }

  /**
   * Возвращает указанное кол-во последних тиков
   * @param count
   * @return
   */
  public List<Tick> getListTickByCount(int count) {
    return new ArrayList<>(listTicks.stream().skip(listTicks.size() - count).collect(Collectors.toList()));
  }

  /**
   * Возвращает тики в указанном диапозоне времени
   * @param timeFrom
   * @param timeTo
   * @return
   */
  public List<Tick> getListTickByTime(Long timeFrom, Long timeTo) {
    return new ArrayList<>(listTicks.stream()
        .filter(t -> t.getTimestamp() <= timeFrom && t.getTimestamp() >= timeTo).toList());
  }

  /**
   * Возвращает тики от последнего до указанной дельты
   * @param timeTo
   * @return
   */
  public List<Tick> getListTickByTimeFromLastTick(Long timeTo) {
    Long timeStartSelection = listTicks.getLast().getTimestamp();
    return new ArrayList<>(listTicks.stream()
        .filter(t -> t.getTimestamp() <= timeStartSelection && t.getTimestamp() >= timeStartSelection - timeTo).toList());
  }


}

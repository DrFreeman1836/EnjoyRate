package main.service;

import java.util.List;
import main.model.Tick;

/**
 * Сервис для работы с тиками
 */
public interface ManagerTicks {

  /**
   * @param tick
   *
   * @return id
   */
  int addTick(Tick tick);

  /**
   * @param listTicks
   */
  void addAllTick(List<Tick> listTicks);

  /**
   * @return Tick
   */
  Tick getLastTick();

}

package main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.enam.TypeSignalActivity;
import main.model.Tick;
import main.storage.impl.TickManagerServiceImpl;

public abstract class AbstractPatternPassivity implements PatternPrice {

  protected Integer countFirst;
  protected Long timeFirst;
  protected Integer countSecond;
  protected Long timeSecond;

  protected List<Tick> listTicks;

  private TickManagerServiceImpl tickManagerService;

  public AbstractPatternPassivity(TickManagerServiceImpl tickManagerService) {
    this.tickManagerService = tickManagerService;
  }

  @Override
  public void setParams(HashMap<String, Number> params) {
    this.countFirst = params.get("countFirst").intValue();
    this.timeFirst = params.get("timeFirst").longValue();
    this.countSecond = params.get("countSecond").intValue();
    this.timeSecond = params.get("timeSecond").longValue();
  }

  @Override
  public int getResponse() {
    getSelection();
    return 0;
  }

  private void getSelection() {
    listTicks = tickManagerService.getListTickByTime(System.currentTimeMillis(), timeFirst + timeSecond);
  }

  private boolean getPatternByAsk() {

    return false;
  }

  public HashMap<String, Number> getParams() {
    return new HashMap<>(Map.of(
        "countFirst", countFirst,
        "timeFirst", timeFirst,
        "countSecond", countSecond,
        "timeSecond", timeSecond
    ));
  }

}
/**
 залипание - Проверка на больше чем Ннное количество тиков в заданное колличество миллисекунд - если проверка пройдена
 проверяем новую переменную времени в которой было не больше чем заданное колличество тиков в ед времени.
 ставим разноцветные нолики - по аску по биду или вместе в настройках
 */

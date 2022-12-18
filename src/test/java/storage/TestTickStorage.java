package storage;

import java.math.BigDecimal;
import main.storage.impl.TickManagerServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class TestTickStorage {

  TickManagerServiceImpl tickService = new TickManagerServiceImpl();

  @Before
  public void initData() throws Exception {
    tickService.processingTick(BigDecimal.valueOf(0.00025), BigDecimal.valueOf(0.00025), 0L);
    tickService.processingTick(BigDecimal.valueOf(0.00028), BigDecimal.valueOf(0.00028), 300L);
    tickService.processingTick(BigDecimal.valueOf(0.00028), BigDecimal.valueOf(0.00028), 500L);
    tickService.processingTick(BigDecimal.valueOf(0.00028), BigDecimal.valueOf(0.00028), 700L);
  }

  @Test
  public void getListTickByCountTest() {

  }

}

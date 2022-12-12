package first;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import main.Application;
import main.enam.TypeSignalActivity;
import main.service.impl.first.FirstPattern;
import main.storage.impl.TickManagerServiceImpl;
import main.telegram.impl.TelegramBotMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TestPatternActivity {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TelegramBotMessages botMessages;

  @Autowired
  private FirstPattern firstPattern;

  @Autowired
  private TickManagerServiceImpl tickManagerService;

  @Test
  public void getResponseTest1() throws Exception {

    HashMap<String, Number> params = new HashMap<>(Map.of(
        "time", 1000,
        "count", 3,
        "deltaMaxAsk", BigDecimal.valueOf(0.00075),
        "deltaMinAsk", BigDecimal.valueOf(0.00009),
        "deltaMaxBid", BigDecimal.valueOf(0.00075),
        "deltaMinBid", BigDecimal.valueOf(0.00009)));
    firstPattern.initParams(params);

    tickManagerService.processingTick(BigDecimal.valueOf(0.00025), BigDecimal.valueOf(0.00025), 0L);
    tickManagerService.processingTick(BigDecimal.valueOf(0.00028), BigDecimal.valueOf(0.00028), 350L);
    tickManagerService.processingTick(BigDecimal.valueOf(0.00035), BigDecimal.valueOf(0.00035), 550L);
//    int res = firstPattern.getResponse();
//    Assertions.assertEquals(TypeSignalActivity.BID.getResponseCode(), res);
    String requestt = "http://localhost:80/api/v1/ea/signal/activity?time=1000&count=3&deltaMaxAsk=0.00075&deltaMinAsk=0.00009&deltaMaxBid=0.00075&deltaMinBid=0.00009";
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI.create(requestt));
    ResultActions result = mockMvc.perform(request);
    result.andExpect(MockMvcResultMatchers.status().is(202));

  }

}

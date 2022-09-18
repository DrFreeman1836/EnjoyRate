import java.net.URI;
import main.Application;
import main.service.impl.PatternDeltaPrice;
import main.service.impl.TelegramBotMessages;
import main.service.impl.TickManagerServiceImpl;
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
public class TestPatternDeltaPrice {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TickManagerServiceImpl managerService;

  @MockBean
  private TelegramBotMessages botMessages;

  @MockBean
  private PatternDeltaPrice patternDeltaPrice;

  @Test
  public void postTick() throws Exception {
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("http://localhost:80/api/v1/ea/test");
    ResultActions result = mockMvc.perform(request);
    result.andExpect(MockMvcResultMatchers.status().is(200));
  }


}

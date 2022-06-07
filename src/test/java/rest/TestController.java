package rest;

import main.Application;
import main.service.ManagerApplication;
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
public class TestController {

  @MockBean
  private ManagerApplication manager;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getGiphy() throws Exception {
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/giphy");
    ResultActions result = mockMvc.perform(request);
    result.andExpect(MockMvcResultMatchers.status().is(500));
  }

}

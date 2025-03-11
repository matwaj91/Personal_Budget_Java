package PersonalBudget.business.user.domain.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    public static final String LOGIN_PAGE = "index";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLoginPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/login"))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE));
    }
}
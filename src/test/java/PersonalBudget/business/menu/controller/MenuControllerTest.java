package PersonalBudget.business.menu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class MenuControllerTest {

    public static final String MAIN_MENU_PAGE = "menu/main";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    public void getMenuPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name(MAIN_MENU_PAGE));
    }
}
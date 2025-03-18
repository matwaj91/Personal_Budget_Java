package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.dto.UserProfileDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserAccountControllerTest {

    private static final String USER_ACCOUNT_PAGE = "menu/userAccount";
    private static final String USER_PROFILE_PAGE = "menu/userProfile";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUserAccountPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/user-account"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/api/v1/login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getUserAccountPageSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/user-account"))
                .andExpect(status().isOk())
                .andExpect((view().name(USER_ACCOUNT_PAGE)));
    }

    @Test
    public void getUserProfilePageTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/user-account/profile"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/api/v1/login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getUserProfilePageSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/user-account/profile"))
                .andExpect(status().isOk())
                .andExpect((view().name(USER_PROFILE_PAGE)));
    }

    @Test
    public void userProfileDTOTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/user-account/profile"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/api/v1/login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void userProfileDTOSuccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/user-account/profile")
                .param("name", "Mateusz")
                .param("password", "123456789"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userProfileDTO"))
                .andExpect(model().attribute("userProfileDTO", new UserProfileDTO("Mateusz", "123456789")));
    }

    @Test
    public void getProperPageAfterProfilePageSubmitTest() throws Exception {
        mockMvc.perform(post("/api/v1/menu/user-account/profile"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/api/v1/login"));
    }
}

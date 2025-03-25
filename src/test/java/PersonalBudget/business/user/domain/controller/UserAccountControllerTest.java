package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.pageHandler.UserAccountPageHandler;
import PersonalBudget.business.user.domain.service.pageHandler.UserProfilePageHandler;
import PersonalBudget.business.user.dto.UserProfileDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserAccountControllerTest {

    private static final String USER_ACCOUNT_PAGE = "menu/userAccount";
    private static final String USER_PROFILE_PAGE = "menu/userProfile";
    private static final String USER_INDEX_PAGE = "index";

    @Mock
    private Model model;

    @Mock
    private UserProfilePageHandler userProfilePageHandler;

    @Mock
    private UserAccountPageHandler userAccountPageHandler;

    @InjectMocks
    private UserAccountController userAccountController;

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

    @Test
    @WithMockUser(username = "user")
    public void getUserProfileSuccessPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/user-account/profile/success"))
                .andExpect(status().isOk())
                .andExpect((view().name(USER_PROFILE_PAGE)));
    }

    @Test
    public void getProperPageAfterSignUpTest() {
        when(userAccountPageHandler.handleUserTransactionsPageAfterSubmit()).thenReturn(USER_ACCOUNT_PAGE);

        String result = userAccountController.getProperPageAfterSignUp();

        assertEquals(USER_ACCOUNT_PAGE, result);
    }

    @Test
    public void getUserTransactionsSuccessPageTest() {
        when(userAccountPageHandler.handleUserTransactionsSuccessPage(model)).thenReturn(USER_ACCOUNT_PAGE);

        String result = userAccountController.getUserTransactionsSuccessPage(model);

        assertEquals(USER_ACCOUNT_PAGE, result);
    }

    @Test
    public void getProperPageAfterAccountDeletionTest() {
        when(userAccountPageHandler.handleUserAccountDeletionPageAfterSubmit()).thenReturn(USER_INDEX_PAGE);

        String result = userAccountController.getProperPageAfterAccountDeletion();

        assertEquals(USER_INDEX_PAGE, result);
    }

    @Test
    public void getUserAccountDeletionSuccessPage() {
        when(userAccountPageHandler.handleUserAccountDeletionSuccessPage(model)).thenReturn(USER_INDEX_PAGE);

        String result = userAccountController.getUserAccountDeletionSuccessPage(model);

        assertEquals(USER_INDEX_PAGE, result);
    }
}

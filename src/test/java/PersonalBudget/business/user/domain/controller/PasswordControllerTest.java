package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.dto.UserEmailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;

import java.util.Optional;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class PasswordControllerTest {

    private static final String PASSWORD_RESET_PAGE = "password/reset";
    private static final String LOGIN_PAGE = "index";
    private static final String PASSWORD_RESET_SUCCESS_PAGE = "password/resetSuccess";
    private static final String FORGOT_PASSWORD_PAGE = "password/forgot";

    private MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session);
    }

    @Test
    public void getResetPasswordPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/password/forgot"))
                .andExpect(status().isOk())
                .andExpect((view().name(FORGOT_PASSWORD_PAGE)));
    }

    @Test
    public void userEmailDTOTest() throws Exception {
        mockMvc.perform(get("/api/v1/password/forgot").param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userEmailDTO"))
                .andExpect(model().attribute("userEmailDTO", new UserEmailDTO("test@example.com")));
    }

    @Test
    public void getProperPageAfterForgotPasswordTest() throws Exception {
        mockMvc.perform(post("/api/v1/password/forgot").param("email", "test@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProperPageAfterResetLinkSubmitTest() throws Exception {
        mockMvc.perform(get("/api/v1/password/reset").param("token", "123456789"))
                .andExpect(status().isOk())
                .andExpect(view().name(anyOf(is(PASSWORD_RESET_PAGE), is(LOGIN_PAGE))));
    }

    @Test
    public void getProperPageAfterResetPasswordSubmitTest() throws Exception {
        Optional<String> optionalEmail = Optional.of("test@example.com");
        session.setAttribute("email", optionalEmail.get());
        assertEquals("test@example.com", session.getAttribute("email"));

        mockMvc.perform(post("/api/v1/password/reset").param("password", "testPass1973!"))
                .andExpect(status().isOk())
                .andExpect(view().name(anyOf(is(PASSWORD_RESET_PAGE), is(PASSWORD_RESET_SUCCESS_PAGE))));
    }
}
package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

    private static final String SIGNUP_PAGE = "signup/signup";
    private static final String SIGNUP_SUCCESS_PAGE = "signup/success";
    private static final String REDIRECT_SIGNUP_SUCCESS_PAGE = "redirect:" + SIGNUP_SUCCESS_PAGE;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getSignUpPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/signup"))
                .andExpect(status().isOk())
                .andExpect((view().name(SIGNUP_PAGE)));
    }

    @Test
    public void userDTOTest() throws Exception {
        mockMvc.perform(get("/api/v1/signup")
                .param("name", "Mateusz")
                .param("email", "test@example.com")
                .param("password", "123456789"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attribute("userDTO", new UserDTO("Mateusz", "test@example.com", "123456789")));
    }

    @Test
    public void getProperPageAfterSignUpTest() throws Exception {
        mockMvc.perform(post("/api/v1/signup")
                .param("name", "Mateusz")
                .param("email", "test@example.com")
                .param("password", "123456789"))
                .andExpect(status().isOk())
                .andExpect(view().name(anyOf(is(SIGNUP_PAGE), is(REDIRECT_SIGNUP_SUCCESS_PAGE))));
    }

    @Test
    public void getSignUpSuccessPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/signup/success"))
                .andExpect(status().isOk())
                .andExpect((view().name(SIGNUP_SUCCESS_PAGE)));
    }
}
package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import PersonalBudget.business.user.dto.UserPasswordDTO;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ResetPasswordPageHandlerTest {

    private final UserPasswordDTO userPasswordDTO = new UserPasswordDTO("testPassword");
    private static final String PASSWORD_RESET_PAGE = "password/reset";
    private static final String LOGIN_PAGE = "index";
    private static final String PASSWORD_RESET_SUCCESS_PAGE = "password/resetSuccess";

    @Mock
    private MockHttpSession session;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserTemplateService userTemplateService;

    @InjectMocks
    private ResetPasswordPageHandler resetPasswordPageHandler;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = mock(HttpSession.class);
        request.setSession(session);
    }

    @Test
    void handleResetPasswordPageEmailEmpty() {
        String token = "123456789";
        when(userService.isAccount(token)).thenReturn(Optional.empty());

        String result = resetPasswordPageHandler.handleResetPasswordPage(token, session);

        assertEquals(LOGIN_PAGE, result);
    }

    @Test
    void handleResetPasswordPageEmailPresent() {
        String token = "123456789";
        when(userService.isAccount(token)).thenReturn(Optional.of("email@example.com"));

        String result = resetPasswordPageHandler.handleResetPasswordPage(token, session);

        assertEquals(PASSWORD_RESET_PAGE, result);
    }

    @Test
    void handleResetPasswordPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO("testPassword");

        String result = resetPasswordPageHandler.handleResetPasswordPageAfterSubmit(bindingResult, model, userPasswordDTO, session);

        assertEquals(PASSWORD_RESET_PAGE, result);
    }

    @Test
    void handleResetPasswordPageTest() {
        when(session.getAttribute("email")).thenReturn("test@example.com");

        String result = resetPasswordPageHandler.handleResetPasswordPageAfterSubmit(bindingResult, model, userPasswordDTO, session);

        assertEquals(PASSWORD_RESET_SUCCESS_PAGE, result);
    }
}
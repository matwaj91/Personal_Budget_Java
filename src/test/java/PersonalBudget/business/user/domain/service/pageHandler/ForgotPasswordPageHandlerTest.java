package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.user.domain.service.EmailService;
import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import PersonalBudget.business.user.dto.UserEmailDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ForgotPasswordPageHandlerTest {

    private final UserEmailDTO userEmailDTO = new UserEmailDTO("example@com");
    private static final String FORGOT_PASSWORD_PAGE = "password/forgot";
    private static final String RESET_REQUESTED_PAGE = "password/resetRequested";

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private EmailService emailService;

    @Mock
    private UserTemplateService userTemplateService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ForgotPasswordPageHandler forgotPasswordPageHandler;

    @Test
    void handleResetPasswordPageTest() {
        String result = forgotPasswordPageHandler.handleResetPasswordPage();

        assertEquals(FORGOT_PASSWORD_PAGE, result);
    }

    @Test
    void handleForgotPasswordPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = forgotPasswordPageHandler.handleForgotPasswordPageAfterSubmit(bindingResult, model, userEmailDTO);

        assertEquals(FORGOT_PASSWORD_PAGE, result);
    }

    @Test
    void handleForgotPasswordPageWithoutEmail() {
        when(userService.isEmail(userEmailDTO.email())).thenReturn(false);

        String result = forgotPasswordPageHandler.handleForgotPasswordPageAfterSubmit(bindingResult, model, userEmailDTO);

        assertEquals(FORGOT_PASSWORD_PAGE, result);
    }


}
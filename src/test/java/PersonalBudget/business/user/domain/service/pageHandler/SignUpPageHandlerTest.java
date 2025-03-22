package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.expense.domain.ExpenseFacade;
import PersonalBudget.business.income.domain.IncomeFacade;
import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.domain.service.EmailService;
import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import PersonalBudget.business.user.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpPageHandlerTest {

    private final UserDTO userDTO = new UserDTO("Mateusz", "example@com", "1253mk@s");
    private static final String SIGNUP_PAGE = "signup/signup";
    private static final String SIGNUP_ACTIVATION_SUCCESS_PAGE = "signup/activation";
    private static final String SIGNUP_SUCCESS_PAGE = "signup/success";
    private static final String REDIRECT_SIGNUP_SUCCESS_PAGE = "redirect:" + SIGNUP_SUCCESS_PAGE;

    @Mock
    private Model model;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserAccountEntity userEntity;

    @Mock
    private UserTemplateService userTemplateService;

    @Mock
    private IncomeFacade incomeFacade;

    @Mock
    private ExpenseFacade expenseFacade;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SignUpPageHandler signUpPageHandler;

    @Test
    void handleSignUpPage() {
        String result = signUpPageHandler.handleSignUpPage();

        assertEquals(SIGNUP_PAGE, result);
    }

    @Test
    void handleSignUpPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = signUpPageHandler.handleSignUpPageAfterSubmit(bindingResult, model, userDTO);

        assertEquals(SIGNUP_PAGE, result);
    }

    @Test
    void handleSignUpPageCheckEmail() {
        when(userService.isEmail(userDTO.email())).thenReturn(true);

        String result = signUpPageHandler.handleSignUpPageAfterSubmit(bindingResult, model, userDTO);

        assertTrue(result.contains(SIGNUP_PAGE));
    }

    @Test
    void handleSignUpSuccessPage() {
        String result = signUpPageHandler.handleSignUpSuccessPage();

        assertTrue(result.contains(SIGNUP_SUCCESS_PAGE));
    }

    @Test
    void handlePageAfterTokenConfirmation() {
        String token = "123456789";
        String result = signUpPageHandler.handlePageAfterTokenConfirmation(token);

        assertTrue(result.contains(SIGNUP_ACTIVATION_SUCCESS_PAGE));
    }
}
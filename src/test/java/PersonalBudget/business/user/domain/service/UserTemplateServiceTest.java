package PersonalBudget.business.user.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class UserTemplateServiceTest {

    @InjectMocks
    private UserTemplateService userTemplateService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        userTemplateService = new UserTemplateService();
        model = mock(Model.class);
    }

    @Test
    public void addEmailVerificationAttributeTest() {
        userTemplateService.addEmailVerificationAttribute(model);

        verify(model).addAttribute("isUserAlreadyRegistered", true);
    }

    @Test
    public void addProfileDetailsAttributeTest() {
        userTemplateService.addProfileDetailsAttribute(model);

        verify(model).addAttribute("isProfileUpdated", true);
    }

    @Test
    public void deleteTransactionsAttributeTest() {
        userTemplateService.deleteTransactionsAttribute(model);

        verify(model).addAttribute("isTransactionDeleted", true);
    }

    @Test
    public void deleteUserAccountAttributeTest() {
        userTemplateService.deleteUserAccountAttribute(model);

        verify(model).addAttribute("isAccountDeleted", true);
    }

    @Test
    public void addMissingEmailAttributeTest() {
        userTemplateService.addMissingEmailAttribute(model);

        verify(model).addAttribute("isUserAlreadyRegistered", false);
    }

    @Test
    public void addNoResetPasswordAttributeTest() {
        userTemplateService.addNoResetPasswordAttribute(model);

        verify(model).addAttribute("isPasswordReset", false);
    }

}
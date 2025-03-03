package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.expense.domain.ExpenseFacade;
import PersonalBudget.business.income.domain.IncomeFacade;
import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class SignUpPageHandler {

    private final UserService userService;
    private final UserTemplateService userTemplateService;
    private final IncomeFacade incomeFacade;
    private final ExpenseFacade expenseFacade;
    private final EmailService emailService;
    private static final String SIGNUP_PAGE = "signup/signup";
    private static final String SIGNUP_ACTIVATION_SUCCESS_PAGE = "signup/activation";
    private static final String SIGNUP_SUCCESS_PAGE = "signup/success";
    private static final String REDIRECT_SIGNUP_SUCCESS_PAGE = "redirect:" + SIGNUP_SUCCESS_PAGE;

    public String handleSignUpPage() {
        return SIGNUP_PAGE;
    }

    public String handleSignUpPageAfterSubmit(BindingResult bindingResult, Model model, UserDTO userDTO) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_PAGE;
        }
        if(userService.isEmail(userDTO)) {
            userTemplateService.addEmailVerificationAttribute(model);
            return SIGNUP_PAGE;
        }
        UserAccountEntity userEntity = userService.addNewUser(userDTO);
        Long id = userEntity.getId();
        String recipient = userEntity.getEmail();
        incomeFacade.addDefaultIncomeCategoriesForUser(id);
        expenseFacade.addDefaultExpenseCategoriesForUser(id);
        expenseFacade.addDefaultPaymentMethodsForUser(id);
        String emailMessage = emailService.buildConfirmationEmail(userEntity);
        String subject = emailService.buildConfirmationSubject();
        emailService.sendEmail(emailMessage, recipient, subject);
        return REDIRECT_SIGNUP_SUCCESS_PAGE;
    }

    public String handleSignUpSuccessPage() {
        return SIGNUP_SUCCESS_PAGE;
    }

    public String handlePageAfterTokenConfirmation(String token) {
        userService.enableUserAccount(token);
        return SIGNUP_ACTIVATION_SUCCESS_PAGE;
    }
}

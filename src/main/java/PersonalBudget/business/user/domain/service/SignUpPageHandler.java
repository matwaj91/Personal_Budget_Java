package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.expense.domain.ExpenseFacade;
import PersonalBudget.business.income.domain.IncomeFacade;
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
    public static final String SIGNUP_PAGE = "signup/signup";
    public static final String SIGNUP_SUCCESS_PAGE = "signup/success";
    public static final String REDIRECT_SIGNUP_SUCCESS_PAGE = "redirect:" + SIGNUP_SUCCESS_PAGE;

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
        Long id = userService.addNewUser(userDTO);
        incomeFacade.addDefaultIncomeCategoriesForUser(id);
        expenseFacade.addDefaultExpenseCategoriesForUser(id);
        expenseFacade.addDefaultPaymentMethodsForUser(id);
        return REDIRECT_SIGNUP_SUCCESS_PAGE;
    }

    public String handleSignUpSuccessPage() {
        return SIGNUP_SUCCESS_PAGE;
    }
}

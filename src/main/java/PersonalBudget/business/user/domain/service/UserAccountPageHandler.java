package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.expense.domain.ExpenseFacade;
import PersonalBudget.business.income.domain.IncomeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserAccountPageHandler {

    private final UserService userService;
    private final UserTemplateService userTemplateService;
    private final IncomeFacade incomeFacade;
    private final ExpenseFacade expenseFacade;
    private static final String USER_ACCOUNT_PAGE = "menu/userAccount";
    private static final String USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE = "transactions/success";
    private static final String REDIRECT_USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE = "redirect:" + USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE;

    public String handleUserAccountPage() {
        return USER_ACCOUNT_PAGE;
    }

    public String handleUserTransactionsPageAfterSubmit(Model model) {
        Long userId = userService.getCurrentLoggedInUserId();
        incomeFacade.deleteIncomesForUser(userId);
        expenseFacade.deleteExpensesForUser(userId);
        return REDIRECT_USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE;
    }

    public String handleUserTransactionsSuccessPage(Model model) {
        userTemplateService.deleteTransactionsAttribute(model);
        return handleUserAccountPage();

    }
}

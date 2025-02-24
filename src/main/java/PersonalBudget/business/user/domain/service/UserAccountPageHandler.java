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
    private static final String USER_ACCOUNT_DELETION_SUCCESS_PAGE = "deletion/success";
    private static final String REDIRECT_USER_ACCOUNT_DELETION_SUCCESS_PAGE = "redirect:" + USER_ACCOUNT_DELETION_SUCCESS_PAGE;
    private static final String USER_INDEX_PAGE = "index";

    public String handleUserAccountPage() {
        return USER_ACCOUNT_PAGE;
    }

    public String handleUserTransactionsPageAfterSubmit() {
        Long loggedInUserId = userService.getCurrentLoggedInUserId();
        deleteAllUserTransactions(loggedInUserId);
        return REDIRECT_USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE;
    }

    public void deleteAllUserTransactions(Long userId) {
        incomeFacade.deleteIncomesForUser(userId);
        expenseFacade.deleteExpensesForUser(userId);
    }

    public void deleteAllUserCategories(Long userId) {
        incomeFacade.deleteIncomeCategoriesForUser(userId);
        expenseFacade.deleteExpenseCategoriesForUser(userId);
    }

    public void deleteAllPaymentMethods(Long userId) {
        expenseFacade.deletePaymentMethodsForUser(userId);
    }

    public String handleUserTransactionsSuccessPage(Model model) {
        userTemplateService.deleteTransactionsAttribute(model);
        return handleUserAccountPage();
    }

    public String handleUserAccountDeletionPageAfterSubmit() {
        Long userId = userService.getCurrentLoggedInUserId();
        deleteAllUserTransactions(userId);
        deleteAllUserCategories(userId);
        deleteAllPaymentMethods(userId);
        userService.deleteUserAccount(userId);
        return REDIRECT_USER_ACCOUNT_DELETION_SUCCESS_PAGE;
    }

    public String handleUserAccountDeletionSuccessPage(Model model) {
        userTemplateService.deleteUserAccountAttribute(model);
        return USER_INDEX_PAGE;
    }
}

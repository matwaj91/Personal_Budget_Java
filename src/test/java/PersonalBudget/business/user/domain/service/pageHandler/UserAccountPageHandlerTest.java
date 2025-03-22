package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.expense.domain.ExpenseFacade;
import PersonalBudget.business.income.domain.IncomeFacade;
import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class UserAccountPageHandlerTest {

    private final Long userId = 1L;
    private static final String USER_ACCOUNT_PAGE = "menu/userAccount";
    private static final String USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE = "transactions/success";
    private static final String REDIRECT_USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE = "redirect:" + USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE;
    private static final String USER_ACCOUNT_DELETION_SUCCESS_PAGE = "deletion/success";
    private static final String REDIRECT_USER_ACCOUNT_DELETION_SUCCESS_PAGE = "redirect:" + USER_ACCOUNT_DELETION_SUCCESS_PAGE;
    private static final String USER_INDEX_PAGE = "index";

    @Mock
    private Model model;

    @Mock
    private UserService userService;

    @Mock
    private UserTemplateService userTemplateService;

    @Mock
    private IncomeFacade incomeFacade;

    @Mock
    private ExpenseFacade expenseFacade;

    @InjectMocks
    private UserAccountPageHandler userAccountPageHandler;

    @Test
    void handleUserAccountPageTest() {
        String result = userAccountPageHandler.handleUserAccountPage();

        assertEquals(USER_ACCOUNT_PAGE, result);
    }

    @Test
    void handleUserTransactionsPageAfterSubmitTest() {
        String result = userAccountPageHandler.handleUserTransactionsPageAfterSubmit();

        assertEquals(REDIRECT_USER_ACCOUNT_TRANSACTIONS_SUCCESS_PAGE, result);
    }

    @Test
    void deleteAllUserTransactionsTest() {
        userAccountPageHandler.deleteAllUserTransactions(userId);

        verify(incomeFacade, times(1)).deleteIncomesForUser(userId);
        verify(expenseFacade, times(1)).deleteExpensesForUser(userId);
    }

    @Test
    void deleteAllUserCategoriesTest() {
        userAccountPageHandler.deleteAllUserCategories(userId);

        verify(incomeFacade, times(1)).deleteIncomeCategoriesForUser(userId);
        verify(expenseFacade, times(1)).deleteExpenseCategoriesForUser(userId);
    }

    @Test
    void deleteAllPaymentMethodsTest() {
        userAccountPageHandler.deleteAllPaymentMethods(userId);

        verify(expenseFacade, times(1)).deletePaymentMethodsForUser(userId);
    }

    @Test
    void handleUserTransactionsSuccessPageTest() {
        String result = userAccountPageHandler.handleUserTransactionsSuccessPage(model);

        assertEquals(USER_ACCOUNT_PAGE, result);
    }

    @Test
    void handleUserAccountDeletionPageAfterSubmitTest() {
        String result = userAccountPageHandler.handleUserAccountDeletionPageAfterSubmit();

        assertEquals(REDIRECT_USER_ACCOUNT_DELETION_SUCCESS_PAGE, result);
    }

    @Test
    void handleUserAccountDeletionSuccessPageTest() {
        String result = userAccountPageHandler.handleUserAccountDeletionSuccessPage(model);

        assertEquals(USER_INDEX_PAGE, result);
    }
}
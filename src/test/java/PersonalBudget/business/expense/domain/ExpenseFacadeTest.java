package PersonalBudget.business.expense.domain;

import PersonalBudget.business.expense.domain.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseFacadeTest {

    private final Long userId = 1L;

    @InjectMocks
    private ExpenseFacade expenseFacade;

    @Mock
    private ExpenseService expenseService;

    @Test
    void addDefaultExpenseCategoriesForUserTest() {
        expenseFacade.addDefaultExpenseCategoriesForUser(userId);

        verify(expenseService, times(1)).addDefaultExpenseCategoriesToUserAccount(userId);
    }

    @Test
    void addDefaultPaymentMethodsForUserTest() {
        expenseFacade.addDefaultPaymentMethodsForUser(userId);

        verify(expenseService, times(1)).addDefaultPaymentMethodsToUserAccount(userId);
    }

    @Test
    void deleteExpensesForUserTest() {
        expenseFacade.deleteExpensesForUser(userId);

        verify(expenseService, times(1)).deleteUserExpenses(userId);
    }

    @Test
    void deleteExpenseCategoriesForUserTest() {
        expenseFacade.deleteExpenseCategoriesForUser(userId);

        verify(expenseService, times(1)).deleteUserExpenseCategories(userId);
    }

    @Test
    void deletePaymentMethodsForUserTest() {
        expenseFacade.deletePaymentMethodsForUser(userId);

        verify(expenseService, times(1)).deleteUserPaymentMethods(userId);
    }
}
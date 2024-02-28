package PersonalBudget.business.expense.domain;

import PersonalBudget.business.expense.domain.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseFacade {

    private final ExpenseService expenseService;

    public void addDefaultExpenseCategoriesForUser(Long userId) {
        expenseService.addDefaultExpenseCategoriesToUserAccount(userId);
    }

    public void addDefaultPaymentMethodsForUser(Long userId) {
        expenseService.addDefaultPaymentMethodsToUserAccount(userId);
    }
}

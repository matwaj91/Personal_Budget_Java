package PersonalBudget.business.expense.domain;

import PersonalBudget.business.expense.domain.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseFacade {

    private final ExpenseService expenseService;

    public void addDefaultExpenseCategoriesForUser(Long id) {
        expenseService.addDefaultExpenseCategoriesToUserAccount(id);
    }

    public void addDefaultPaymentMethodsForUser(Long id) {
        expenseService.addDefaultPaymentMethodsToUserAccount(id);
    }
}

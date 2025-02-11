package PersonalBudget.business.expense.domain.mapper;

import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import PersonalBudget.business.expense.domain.model.ExpensePaymentMethodEntity;
import PersonalBudget.business.expense.dto.ExpenseNewCategoryDTO;
import PersonalBudget.business.expense.dto.ExpenseNewPaymentMethodDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCategoryMapper {

    public ExpenseCategoryEntity mapExpenseCategoryDTOToExpenseCategoryEntity(@Valid ExpenseNewCategoryDTO expenseNewCategoryDTO, Long userId) {
        return ExpenseCategoryEntity.builder()
                .userId(userId)
                .name(expenseNewCategoryDTO.name())
                .build();
    }

    public ExpensePaymentMethodEntity mapExpensePaymentMethodDTOToExpensePaymentMethodEntity(@Valid ExpenseNewPaymentMethodDTO expenseNewPaymentMethodDTO, Long userId) {
        return ExpensePaymentMethodEntity.builder()
                .userId(userId)
                .name(expenseNewPaymentMethodDTO.name())
                .build();
    }
}

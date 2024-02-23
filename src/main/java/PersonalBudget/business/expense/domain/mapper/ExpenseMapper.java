package PersonalBudget.business.expense.domain.mapper;

import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseEntity mapExpenseDTOToExpenseEntity(ExpenseDTO expenseDTO, Long userId, Long userExpenseCategory, Long userPaymentMethod) {
        return ExpenseEntity.builder()
                .userId(userId)
                .expenseCategoryId(userExpenseCategory)
                .expensePaymentMethodId(userPaymentMethod)
                .amount(expenseDTO.amount())
                .expenseDate(expenseDTO.expenseDate())
                .expenseComment(expenseDTO.expenseComment())
                .build();
    }
}

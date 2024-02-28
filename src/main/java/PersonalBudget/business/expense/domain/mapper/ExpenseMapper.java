package PersonalBudget.business.expense.domain.mapper;

import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseEntity mapExpenseDTOToExpenseEntity(ExpenseDTO expenseDTO, Long userId, Long userExpenseCategoryId, Long userPaymentMethodId) {
        return ExpenseEntity.builder()
                .userId(userId)
                .expenseCategoryId(userExpenseCategoryId)
                .expensePaymentMethodId(userPaymentMethodId)
                .amount(expenseDTO.amount())
                .expenseDate(expenseDTO.expenseDate())
                .expenseComment(expenseDTO.expenseComment())
                .build();
    }
}

package PersonalBudget.business.expense.domain.mapper;

import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseEntity mapExpenseDTOToExpenseEntity(ExpenseDTO expenseDTO, Long userId) {
        return ExpenseEntity.builder()
                .userId(userId)
                .expenseCategoryId(expenseDTO.expenseCategoryId())
                .expensePaymentMethodId(expenseDTO.paymentMethodId())
                .amount(expenseDTO.amount())
                .date(expenseDTO.date())
                .expenseComment(expenseDTO.expenseComment())
                .build();
    }
}

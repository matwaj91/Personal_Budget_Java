package PersonalBudget.business.expense.domain.mapper;

import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import PersonalBudget.business.expense.dto.ExpenseNewCategoryDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCategoryMapper {

    public ExpenseCategoryEntity mapExpenseCategoryDTOToIncomeCategoryEntity(@Valid ExpenseNewCategoryDTO expenseNewCategoryDTO, Long userId) {
        return ExpenseCategoryEntity.builder()
                .userId(userId)
                .name(expenseNewCategoryDTO.name())
                .build();
    }
}

package PersonalBudget.business.expense.dto;

import java.math.BigDecimal;

public record ExpenseCategoryDTO (

        Long id,
        String expenseCategory,
        BigDecimal limitAmount
) {}

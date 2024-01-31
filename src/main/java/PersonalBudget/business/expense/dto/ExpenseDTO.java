package PersonalBudget.business.expense.dto;

import jakarta.validation.constraints.NotEmpty;

public record ExpenseDTO(

        @NotEmpty(message = "{amount.empty}")
        String amount,
        @NotEmpty(message = "{date.empty}")
        String expenseDate,
        @NotEmpty(message = "{payment.empty}")
        String paymentMethod,
        @NotEmpty(message = "{category.empty}")
        String category,
        String expenseComment) {
}

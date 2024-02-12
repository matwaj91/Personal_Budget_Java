package PersonalBudget.business.expense.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDTO(

        @NotNull(message = "{expense.amount.empty}")
        BigDecimal amount,
        @NotNull(message = "{expense.date.empty}")
        LocalDate expenseDate,
        @NotEmpty(message = "{expense.payment.empty}")
        String paymentMethod,
        @NotEmpty(message = "{expense.category.empty}")
        String category,
        String expenseComment) {
}

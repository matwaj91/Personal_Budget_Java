package PersonalBudget.business.expense.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDTO(

        @NotNull(message = "{expense.amount.empty}")
        BigDecimal amount,
        @NotNull(message = "{expense.date.empty}")
        LocalDate date,
        @NotNull(message = "{expense.payment.empty}")
        Long paymentMethodId,
        @NotNull(message = "{expense.category.empty}")
        Long expenseCategoryId,
        String expenseComment) {
}

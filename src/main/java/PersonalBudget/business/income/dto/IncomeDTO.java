package PersonalBudget.business.income.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeDTO (

        @NotNull(message = "{income.amount.empty}")
        BigDecimal amount,
        @NotNull(message = "{income.date.empty}")
        LocalDate incomeDate,
        @NotNull(message = "{income.category.empty}")
        Long incomeCategoryId,
        String incomeComment) {
}

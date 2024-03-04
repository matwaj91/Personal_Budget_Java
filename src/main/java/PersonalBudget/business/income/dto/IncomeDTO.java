package PersonalBudget.business.income.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeDTO(

        @NotEmpty(message = "{income.amount.empty}")
        BigDecimal amount,
        @NotEmpty(message = "{income.date.empty}")
        LocalDate incomeDate,
        @NotEmpty(message = "{income.category.empty}")
        Long incomeCategoryId,
        String incomeComment) {
}

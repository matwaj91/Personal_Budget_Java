package PersonalBudget.business.income.dto;

import jakarta.validation.constraints.NotEmpty;

public record IncomeDTO(

        @NotEmpty(message = "{income.amount.empty}")
        String amount,
        @NotEmpty(message = "{income.date.empty}")
        String incomeDate,
        @NotEmpty(message = "{income.category.empty}")
        String category,
        String incomeComment) {
}

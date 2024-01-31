package PersonalBudget.business.income.dto;

import jakarta.validation.constraints.NotEmpty;

public record IncomeDTO(

        @NotEmpty(message = "{amount.empty}")
        String amount,
        @NotEmpty(message = "{date.empty}")
        String incomeDate,
        @NotEmpty(message = "{category.empty}")
        String category,
        String incomeComment) {
}

package PersonalBudget.business.income.dto;

import jakarta.validation.constraints.NotEmpty;

public record IncomeCategoryDTO(

        Long id,
        String incomeCategory
) {}

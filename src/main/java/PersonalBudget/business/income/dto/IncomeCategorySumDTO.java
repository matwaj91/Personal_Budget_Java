package PersonalBudget.business.income.dto;

import java.math.BigDecimal;

public record IncomeCategorySumDTO(

        String name,
        BigDecimal amount
) {}

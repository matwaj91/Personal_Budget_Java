package PersonalBudget.business.income.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeParticularDTO(

        BigDecimal amount,
        LocalDate incomeDate,
        String name
) {}

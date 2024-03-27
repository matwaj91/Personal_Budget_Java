package PersonalBudget.business.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseParticularDTO(

        BigDecimal amount,
        LocalDate expenseDate,
        String name
) {}

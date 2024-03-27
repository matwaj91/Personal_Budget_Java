package PersonalBudget.business.balance.domain;

import PersonalBudget.business.expense.domain.service.ExpenseService;
import PersonalBudget.business.expense.dto.ExpenseParticularDTO;
import PersonalBudget.business.income.domain.service.IncomeService;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import PersonalBudget.common.util.CategorySumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BalanceGateway {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public List<CategorySumDTO> fetchUserIncomeCategoriesSums(LocalDate dateFrom, LocalDate dateTo) {
        return incomeService.getUserIncomeCategoriesSums(dateFrom, dateTo);
    }

    public List<IncomeParticularDTO> fetchUserParticularsIncomeCategory(LocalDate dateFrom, LocalDate dateTo) {
        return incomeService.getUserParticularsIncomeCategory(dateFrom, dateTo);
    }

    public List<CategorySumDTO> fetchUserExpenseCategoriesSums(LocalDate dateFrom, LocalDate dateTo) {
        return expenseService.getUserExpenseCategoriesSums(dateFrom, dateTo);
    }

    public List<ExpenseParticularDTO> fetchUserParticularsExpenseCategory(LocalDate dateFrom, LocalDate dateTo) {
        return expenseService.getUserParticularsExpenseCategory(dateFrom, dateTo);
    }

}

package PersonalBudget.business.balance.domain;

import PersonalBudget.business.income.domain.service.IncomeService;
import PersonalBudget.business.income.dto.IncomeCategorySumDTO;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BalanceGateway {

    private final IncomeService incomeService;

    public List<IncomeCategorySumDTO> fetchUserIncomeCategoriesSums (LocalDate dateFrom, LocalDate dateTo) {
        return incomeService.getUserIncomeCategoriesSums(dateFrom, dateTo);
    }

    public List<IncomeParticularDTO> fetchUserParticularsIncomeCategory (LocalDate dateFrom, LocalDate dateTo) {
        return incomeService.getUserParticularsIncomeCategory(dateFrom, dateTo);
    }
}

package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.income.dto.IncomeCategorySumDTO;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BalanceTemplateService {

    public void addIncomeCategoriesSumAttribute(Model model, List<IncomeCategorySumDTO> incomeCategoriesSum) {
        model.addAttribute("incomeCategoriesSum", incomeCategoriesSum);
    }

    public void addIncomeSumAttribute(Model model, List<IncomeCategorySumDTO> incomeCategoriesSum) {
        BigDecimal incomeSum = new BigDecimal(0);
        for(IncomeCategorySumDTO incomeCategorySum : incomeCategoriesSum) {
            incomeSum = incomeSum.add(incomeCategorySum.amount());
        }
        model.addAttribute("incomeSum", incomeSum);
    }

    public void addIncomeParticularAttribute(Model model, List<IncomeParticularDTO> particularIncomes) {
        model.addAttribute("particularIncomes", particularIncomes);
    }

    public void addIncomeChartDataAttribute(Model model, List<List<Object>> charData) {
        model.addAttribute("charData", charData);
    }
}

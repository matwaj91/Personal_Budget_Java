package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.expense.dto.ExpenseParticularDTO;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import PersonalBudget.common.util.CategorySumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BalanceTemplateService {

    public void addIncomeCategoriesSumAttribute(Model model, List<CategorySumDTO> incomeCategoriesSum) {
        model.addAttribute("incomeCategoriesSum", incomeCategoriesSum);
    }

    public void addIncomeSumAttribute(Model model, List<CategorySumDTO> incomeCategoriesSum) {
        BigDecimal incomeSum = new BigDecimal(0);
        for(CategorySumDTO incomeCategorySum : incomeCategoriesSum) {
            incomeSum = incomeSum.add(incomeCategorySum.amount());
        }
        model.addAttribute("incomeSum", incomeSum);
    }

    public void addIncomeParticularAttribute(Model model, List<IncomeParticularDTO> particularIncomes) {
        model.addAttribute("particularIncomes", particularIncomes);
    }

    public void addIncomeChartDataAttribute(Model model, List<List<Object>> incomeChartData) {
        model.addAttribute("incomeChartData", incomeChartData);
    }

    public void addExpenseParticularAttribute(Model model, List<ExpenseParticularDTO> particularExpenses) {
        model.addAttribute("particularExpenses", particularExpenses);
    }

    public void addExpenseSumAttribute(Model model, List<CategorySumDTO> expenseCategoriesSum) {
        BigDecimal expenseSum = new BigDecimal(0);
        for(CategorySumDTO expenseCategorySum : expenseCategoriesSum) {
           expenseSum = expenseSum.add(expenseCategorySum.amount());
        }
        model.addAttribute("expenseSum", expenseSum);
    }

    public void addExpenseCategoriesSumAttribute(Model model, List<CategorySumDTO> expenseCategoriesSum) {
        model.addAttribute("expenseCategoriesSum", expenseCategoriesSum);
    }

    public void addExpenseChartDataAttribute(Model model, List<List<Object>> expenseChartData) {
        model.addAttribute("expenseChartData", expenseChartData);
    }
}

package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.balance.domain.BalanceGateway;
import PersonalBudget.business.expense.dto.ExpenseParticularDTO;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import PersonalBudget.common.util.CategorySumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

import static PersonalBudget.common.util.Date.getFirstDayCurrentMonth;
import static PersonalBudget.common.util.Date.getFirstDayPreviousMonth;
import static PersonalBudget.common.util.Date.getLastDayCurrentMonth;
import static PersonalBudget.common.util.Date.getLastDayPreviousMonth;

@RequiredArgsConstructor
@Service
public class BalancePageHandler {

    private final BalanceGateway balanceGateway;
    private final BalanceTemplateService balanceTemplateService;
    private static final String BALANCE_PAGE = "menu/balance";


    public String handleCurrentMonthBalancePage(Model model) {
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo= getLastDayCurrentMonth();
        handleBalancePage(model, dateFrom, dateTo);
        return BALANCE_PAGE;
    }

    public String handlePreviousMonthBalancePage(Model model) {
        LocalDate dateFrom = getFirstDayPreviousMonth();
        LocalDate dateTo = getLastDayPreviousMonth();
        handleBalancePage(model, dateFrom, dateTo);
        return BALANCE_PAGE;
    }

    public void handleBalancePage(Model model, LocalDate dateFrom, LocalDate dateTo) {
        List<CategorySumDTO> incomeCategoriesSum  = balanceGateway.fetchUserIncomeCategoriesSums(dateFrom, dateTo);
        List<CategorySumDTO> expenseCategoriesSum = balanceGateway.fetchUserExpenseCategoriesSums(dateFrom, dateTo);
        List<List<Object>> incomeChartData = mapToChartData(incomeCategoriesSum);
        List<List<Object>> expenseChartData = mapToChartData(expenseCategoriesSum);
        List<IncomeParticularDTO> particularIncomes = balanceGateway.fetchUserParticularsIncomeCategory(dateFrom, dateTo);
        List<ExpenseParticularDTO> particularExpenses = balanceGateway.fetchUserParticularsExpenseCategory(dateFrom, dateTo);
        balanceTemplateService.addIncomeParticularAttribute(model, particularIncomes);
        balanceTemplateService.addExpenseParticularAttribute(model, particularExpenses);
        balanceTemplateService.addIncomeSumAttribute(model, incomeCategoriesSum);
        balanceTemplateService.addExpenseSumAttribute(model, expenseCategoriesSum);
        balanceTemplateService.addIncomeCategoriesSumAttribute(model, incomeCategoriesSum);
        balanceTemplateService.addExpenseCategoriesSumAttribute(model, expenseCategoriesSum);
        balanceTemplateService.addIncomeChartDataAttribute(model, incomeChartData);
        balanceTemplateService.addExpenseChartDataAttribute(model, expenseChartData);
    }

    private List<Object> mapSumDTONameAndAmountToList(CategorySumDTO incomeCategorySumDTO) {
        return List.of(
                incomeCategorySumDTO.name(), incomeCategorySumDTO.amount()
        );
    }

    private List<List<Object>> mapToChartData(List<CategorySumDTO> categoriesSum) {
        return categoriesSum.stream()
                .map(this::mapSumDTONameAndAmountToList)
                .toList();
    }
}

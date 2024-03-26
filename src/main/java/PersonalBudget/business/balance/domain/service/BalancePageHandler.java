package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.balance.domain.BalanceGateway;
import PersonalBudget.business.income.dto.IncomeCategorySumDTO;
import PersonalBudget.business.income.dto.IncomeParticularDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

import static PersonalBudget.common.util.Date.getCurrentMonth;
import static PersonalBudget.common.util.Date.getCurrentYear;
import static PersonalBudget.common.util.Date.getLastDayCurrentMonth;

@RequiredArgsConstructor
@Service
public class BalancePageHandler {

    private final BalanceGateway balanceGateway;
    private final BalanceTemplateService balanceTemplateService;
    private static final String BALANCE_PAGE = "menu/balance";

    public String handleCurrentMonthBalancePage(Model model) {
        String currentYear = getCurrentYear();
        String currentMonth = getCurrentMonth();
        String lastDayCurrentMonth = getLastDayCurrentMonth();
        LocalDate dateFrom = LocalDate.parse(currentYear + "-" + currentMonth + "-" + "01");
        LocalDate dateTo = LocalDate.parse(currentYear + "-" + currentMonth + "-" + lastDayCurrentMonth);
        List<IncomeCategorySumDTO> incomeCategoriesSum  = balanceGateway.fetchUserIncomeCategoriesSums(dateFrom, dateTo);
        List<List<Object>> chartData = mapToChartData(incomeCategoriesSum);
        List<IncomeParticularDTO> particularIncomes = balanceGateway.fetchUserParticularsIncomeCategory(dateFrom, dateTo);
        balanceTemplateService.addIncomeParticularAttribute(model, particularIncomes);
        balanceTemplateService.addIncomeSumAttribute(model, incomeCategoriesSum);
        balanceTemplateService.addIncomeCategoriesSumAttribute(model, incomeCategoriesSum);
        balanceTemplateService.addIncomeChartDataAttribute(model, chartData);
        return BALANCE_PAGE;
    }

    private List<Object> mapSumDTONameAndAmountToList(IncomeCategorySumDTO incomeCategorySumDTO) {
        return List.of(
                incomeCategorySumDTO.name(), incomeCategorySumDTO.amount()
        );
    }

    private List<List<Object>> mapToChartData(List<IncomeCategorySumDTO> incomeCategoriesSums) {
        return incomeCategoriesSums.stream()
                .map(this::mapSumDTONameAndAmountToList)
                .toList();
    }
}

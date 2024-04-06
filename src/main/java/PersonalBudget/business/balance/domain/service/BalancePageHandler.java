package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.balance.domain.BalanceGateway;
import PersonalBudget.business.balance.domain.mapper.BalanceMapper;
import PersonalBudget.common.util.CategorySumDTO;
import PersonalBudget.common.util.TimePeriodDTO;
import PersonalBudget.common.util.ParticularActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

import static PersonalBudget.common.util.PersonalBudgetDateUtils.getFirstDayCurrentMonth;
import static PersonalBudget.common.util.PersonalBudgetDateUtils.getFirstDayCurrentYear;
import static PersonalBudget.common.util.PersonalBudgetDateUtils.getFirstDayPreviousMonth;
import static PersonalBudget.common.util.PersonalBudgetDateUtils.getLastDayCurrentMonth;
import static PersonalBudget.common.util.PersonalBudgetDateUtils.getLastDayCurrentYear;
import static PersonalBudget.common.util.PersonalBudgetDateUtils.getLastDayPreviousMonth;

@RequiredArgsConstructor
@Service
public class BalancePageHandler {

    private final BalanceGateway balanceGateway;
    private final BalanceTemplateService balanceTemplateService;
    private final BalanceMapper balanceMapper;
    private static final String BALANCE_PAGE = "menu/balance";
    private static final String MENU_PAGE = "menu/main";


    public String handleCurrentMonthBalancePage(Model model) {
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo= getLastDayCurrentMonth();
        return handleBalancePage(model, dateFrom, dateTo);
    }

    public String handlePreviousMonthBalancePage(Model model) {
        LocalDate dateFrom = getFirstDayPreviousMonth();
        LocalDate dateTo = getLastDayPreviousMonth();
        return handleBalancePage(model, dateFrom, dateTo);
    }

    public String handleCurrentYearBalancePage(Model model) {
        LocalDate dateFrom = getFirstDayCurrentYear();
        LocalDate dateTo = getLastDayCurrentYear();
        return handleBalancePage(model, dateFrom, dateTo);
    }

    public String handleTimeRangeBalancePage(TimePeriodDTO timePeriodDTO, Model model) {
        LocalDate dateFrom = timePeriodDTO.dateFrom();
        LocalDate dateTo = timePeriodDTO.dateTo();
        if(dateTo.isBefore(dateFrom)) {
            balanceTemplateService.addTwoDatesComparisonAttribute(model);
            return MENU_PAGE;
        }
        return handleBalancePage(model, dateFrom, dateTo);
    }

    public String handleTimeRangeBalancePageWithoutDateRange(Model model) {
        balanceTemplateService.addNoDateRangeAttribute(model);
        return MENU_PAGE;
    }

    public String handleBalancePage(Model model, LocalDate dateFrom, LocalDate dateTo) {
        List<CategorySumDTO> incomeCategoriesSum  = balanceGateway.fetchUserIncomeCategoriesSums(dateFrom, dateTo);
        List<CategorySumDTO> expenseCategoriesSum = balanceGateway.fetchUserExpenseCategoriesSums(dateFrom, dateTo);
        List<List<Object>> incomeChartData = balanceMapper.mapToChartData(incomeCategoriesSum);
        List<List<Object>> expenseChartData = balanceMapper.mapToChartData(expenseCategoriesSum);
        List<ParticularActivityDTO> particularIncomes = balanceGateway.fetchUserParticularsIncomeCategory(dateFrom, dateTo);
        List<ParticularActivityDTO> particularExpenses = balanceGateway.fetchUserParticularsExpenseCategory(dateFrom, dateTo);
        handleTemplateServiceLogic(model, particularIncomes, particularExpenses, incomeCategoriesSum, expenseCategoriesSum, incomeChartData, expenseChartData);
        return BALANCE_PAGE;
    }

    public void handleTemplateServiceLogic(Model model, List<ParticularActivityDTO> particularIncomes, List<ParticularActivityDTO> particularExpenses,
                                           List<CategorySumDTO> incomeCategoriesSum, List<CategorySumDTO> expenseCategoriesSum,
                                           List<List<Object>> incomeChartData, List<List<Object>> expenseChartData) {
        balanceTemplateService.addIncomeParticularAttribute(model, particularIncomes);
        balanceTemplateService.addExpenseParticularAttribute(model, particularExpenses);
        balanceTemplateService.addIncomeSumAttribute(model, incomeCategoriesSum);
        balanceTemplateService.addExpenseSumAttribute(model, expenseCategoriesSum);
        balanceTemplateService.addIncomeCategoriesSumAttribute(model, incomeCategoriesSum);
        balanceTemplateService.addExpenseCategoriesSumAttribute(model, expenseCategoriesSum);
        balanceTemplateService.addIncomeChartDataAttribute(model, incomeChartData);
        balanceTemplateService.addExpenseChartDataAttribute(model, expenseChartData);
    }
}

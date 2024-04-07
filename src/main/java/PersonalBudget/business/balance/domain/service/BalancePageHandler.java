package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.balance.domain.BalanceGateway;
import PersonalBudget.business.balance.domain.mapper.BalanceMapper;
import PersonalBudget.common.util.ParticularActivityDTO;
import PersonalBudget.common.util.TimePeriodDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
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
        List<ParticularActivityDTO> particularIncomes = balanceGateway.fetchUserParticularsIncomeCategory(dateFrom, dateTo);
        List<ParticularActivityDTO> particularExpenses = balanceGateway.fetchUserParticularsExpenseCategory(dateFrom, dateTo);
        List<List<Object>> incomeCategoriesSum = balanceMapper.mapParticularActivityDTOToNameAndTotalAmountList(particularIncomes);
        List<List<Object>> expenseCategoriesSum = balanceMapper.mapParticularActivityDTOToNameAndTotalAmountList(particularExpenses);
        BigDecimal incomesSum = getTotalSum(incomeCategoriesSum);
        BigDecimal expensesSum = getTotalSum(expenseCategoriesSum);
        handleTemplateServiceLogic(model, particularIncomes, particularExpenses, incomesSum, expensesSum, incomeCategoriesSum, expenseCategoriesSum);
        return BALANCE_PAGE;
    }

    private BigDecimal getTotalSum(List<List<Object>> categoriesSum) {
        BigDecimal totalSum = new BigDecimal(0);
        for(List<Object> categorySum : categoriesSum) {
            totalSum = totalSum.add((BigDecimal) categorySum.get(1));
        }
        return totalSum;
    }

    public void handleTemplateServiceLogic(Model model, List<ParticularActivityDTO> particularIncomes, List<ParticularActivityDTO> particularExpenses,
                                           BigDecimal incomesSum, BigDecimal expensesSum, List<List<Object>> incomeCategoriesSum, List<List<Object>> expenseCategoriesSum) {
        balanceTemplateService.addIncomeParticularAttribute(model, particularIncomes);
        balanceTemplateService.addExpenseParticularAttribute(model, particularExpenses);
        balanceTemplateService.addIncomeSumAttribute(model, incomesSum);
        balanceTemplateService.addExpenseSumAttribute(model, expensesSum);
        balanceTemplateService.addIncomeCategoriesSumAttribute(model, incomeCategoriesSum);
        balanceTemplateService.addExpenseCategoriesSumAttribute(model, expenseCategoriesSum);
    }
}

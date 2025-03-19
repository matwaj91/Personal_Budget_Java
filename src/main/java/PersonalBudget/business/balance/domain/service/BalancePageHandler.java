package PersonalBudget.business.balance.domain.service;

import PersonalBudget.common.util.ParticularActivityDTO;
import PersonalBudget.common.util.TimePeriodDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

import static PersonalBudget.common.util.DateUtils.getFirstDayCurrentMonth;
import static PersonalBudget.common.util.DateUtils.getFirstDayCurrentYear;
import static PersonalBudget.common.util.DateUtils.getFirstDayPreviousMonth;
import static PersonalBudget.common.util.DateUtils.getLastDayCurrentMonth;
import static PersonalBudget.common.util.DateUtils.getLastDayCurrentYear;
import static PersonalBudget.common.util.DateUtils.getLastDayPreviousMonth;

@RequiredArgsConstructor
@Service
public class BalancePageHandler {

    private final BalanceTemplateService balanceTemplateService;
    private final BalanceService balanceService;
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
            balanceTemplateService.addWrongDateInputAttribute(model);
            return MENU_PAGE;
        }
        return handleBalancePage(model, dateFrom, dateTo);
    }

    public String handleTimeRangeBalancePageWithoutDateRange(Model model) {
        balanceTemplateService.addNoDateRangeAttribute(model);
        return MENU_PAGE;
    }

    public String handleBalancePage(Model model, LocalDate dateFrom, LocalDate dateTo) {
        List<ParticularActivityDTO> particularIncomes = balanceService.getUserParticularsIncomeCategory(dateFrom, dateTo);
        List<ParticularActivityDTO> particularExpenses = balanceService.getUserParticularsExpensesCategory(dateFrom, dateTo);
        balanceTemplateService.addIncomeParticularAttribute(model, particularIncomes);
        balanceTemplateService.addExpenseParticularAttribute(model, particularExpenses);
        balanceTemplateService.addIncomeSumAttribute(model, particularIncomes);
        balanceTemplateService.addExpenseSumAttribute(model, particularExpenses);
        balanceTemplateService.addIncomeCategoriesSumAttribute(model, particularIncomes);
        balanceTemplateService.addExpenseCategoriesSumAttribute(model, particularExpenses);
        return BALANCE_PAGE;
    }
}

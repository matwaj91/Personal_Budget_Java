package PersonalBudget.business.balance.domain.service;

import PersonalBudget.common.util.ParticularActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BalanceTemplateService {

    private final BalanceService balanceService;

    public void addIncomeParticularAttribute(Model model, List<ParticularActivityDTO> particularIncomes) {
        model.addAttribute("particularIncomes", particularIncomes);
    }

    public void addExpenseParticularAttribute(Model model, List<ParticularActivityDTO> particularExpenses) {
        model.addAttribute("particularExpenses", particularExpenses);
    }

    public void addIncomeSumAttribute(Model model, List<ParticularActivityDTO> particularIncomes) {
        BigDecimal incomesSum = balanceService.calculateTotalSum(particularIncomes);
        model.addAttribute("incomesSum", incomesSum);
    }

    public void addExpenseSumAttribute(Model model, List<ParticularActivityDTO> particularExpenses) {
        BigDecimal expensesSum = balanceService.calculateTotalSum(particularExpenses);
        model.addAttribute("expensesSum", expensesSum);
    }

    public void addIncomeCategoriesSumAttribute(Model model, List<ParticularActivityDTO> particularIncomes) {
        List<List<Object>> incomeCategoriesSum = balanceService.getCategoriesSum(particularIncomes);
        model.addAttribute("incomeCategoriesSum", incomeCategoriesSum);
    }

    public void addExpenseCategoriesSumAttribute(Model model, List<ParticularActivityDTO> particularExpenses) {
        List<List<Object>> expenseCategoriesSum = balanceService.getCategoriesSum(particularExpenses);
        model.addAttribute("expenseCategoriesSum", expenseCategoriesSum);
    }

    public void addWrongDateInputAttribute(Model model) {
        model.addAttribute("wrongDateInput", true);
    }

    public void addNoDateRangeAttribute(Model model) {
        model.addAttribute("isDateRange", false);
    }
}

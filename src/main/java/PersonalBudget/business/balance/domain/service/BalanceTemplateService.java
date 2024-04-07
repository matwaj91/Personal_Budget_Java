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

    public void addIncomeCategoriesSumAttribute(Model model, List<List<Object>> incomeCategoriesSum) {
        model.addAttribute("incomeCategoriesSum", incomeCategoriesSum);
    }

    public void addIncomeSumAttribute(Model model, BigDecimal incomesSum) {
        model.addAttribute("incomesSum", incomesSum);
    }

    public void addIncomeParticularAttribute(Model model, List<ParticularActivityDTO> particularIncomes) {
        model.addAttribute("particularIncomes", particularIncomes);
    }

    public void addExpenseParticularAttribute(Model model, List<ParticularActivityDTO> particularExpenses) {
        model.addAttribute("particularExpenses", particularExpenses);
    }

    public void addExpenseSumAttribute(Model model, BigDecimal expensesSum) {
        model.addAttribute("expensesSum", expensesSum);
    }

    public void addExpenseCategoriesSumAttribute(Model model, List<List<Object>> expenseCategoriesSum) {
        model.addAttribute("expenseCategoriesSum", expenseCategoriesSum);
    }

    public void addTwoDatesComparisonAttribute(Model model) {
        model.addAttribute("wrongDateInput", true);
    }

    public void addNoDateRangeAttribute(Model model) {
        model.addAttribute("isDateRange", false);
    }
}

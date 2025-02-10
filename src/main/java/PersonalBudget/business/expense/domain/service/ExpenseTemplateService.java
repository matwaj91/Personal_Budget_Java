package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseTemplateService {

    private final ExpenseService expenseService;

    public void addExpenseCategoriesAttribute(Model model) {
        List<ExpenseCategoryDTO> expenseCategories = expenseService.getUserExpenseCategories();
        model.addAttribute("expenseCategories", expenseCategories);
    }

    public void addPaymentMethodsAttribute(Model model) {
        List<ExpensePaymentMethodDTO> paymentMethods = expenseService.getUserPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethods);
    }

    public void addExpenseSuccessAttribute(Model model) {
        model.addAttribute("addedExpense", true);
    }

    public void addExpenseCategoriesSuccessAttribute(Model model) {
        model.addAttribute("addedExpenseCategory", true);
    }

    public void addExpenseCategoriesFailureAttribute(Model model) {
        model.addAttribute("additionFailureExpenseCategory", true);
    }

    public void addExpenseCategoriesDeletionSuccessAttribute(Model model) {
        model.addAttribute("deletionExpenseCategory", true);
    }

    public void addExpenseCategoriesDeletionFailureAttribute(Model model) {
        model.addAttribute("deletionFailureExpenseCategory", true);
    }

    public void addSettingLimitSuccessAttribute(Model model) {
        model.addAttribute("settingLimitCategory", true);
    }
}

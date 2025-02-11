package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseTemplateService {

    private final ExpenseService expenseService;

    public void addExpenseCategoriesAttribute(Model model) {
        List<ExpenseCategoryDTO> expenseCategories = expenseService.getUserExpenseCategories();
        model.addAttribute("expenseCategories", expenseCategories);
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

    public void addPaymentMethodsAttribute(Model model) {
        List<ExpensePaymentMethodDTO> expensePaymentMethods = expenseService.getUserPaymentMethods();
        model.addAttribute("expensePaymentMethods", expensePaymentMethods);
    }

    public void addExpensePaymentMethodSuccessAttribute(Model model) {
        model.addAttribute("addedExpensePaymentMethod", true);
    }

    public void addExpensePaymentMethodFailureAttribute(Model model) {
        model.addAttribute("additionFailureExpensePaymentMethod", true);
    }

    public void addExpensePaymentMethodDeletionSuccessAttribute(Model model) {
        model.addAttribute("deletionExpensePaymentMethod", true);
    }

    public void addExpensePaymentMethodDeletionFailureAttribute(Model model) {
        model.addAttribute("deletionFailureExpensePaymentMethod", true);
    }
}

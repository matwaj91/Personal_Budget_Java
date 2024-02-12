package PersonalBudget.business.expense.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseTemplateService {

    private final ExpenseService expenseService;

    public void addExpenseCategoriesAttribute(Model model) {
        List<String> expenseCategories = expenseService.getUserExpenseCategories();
        model.addAttribute("expenseCategories", expenseCategories);
    }

    public void addPaymentMethodsAttribute(Model model) {
        List<String> paymentMethods = expenseService.getUserPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethods);
    }

    public void addExpenseSuccessAttribute(Model model) {
        model.addAttribute("addedExpense", true);
    }
}

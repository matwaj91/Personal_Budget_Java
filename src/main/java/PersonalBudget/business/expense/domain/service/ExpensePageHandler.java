package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class ExpensePageHandler {

    private final ExpenseTemplateService expenseTemplateService;
    private final ExpenseService expenseService;
    private static final String EXPENSE_PAGE = "menu/expense";
    private static final String EXPENSE_SUCCESS_PAGE = "expense/success";
    private static final String REDIRECT_EXPENSE_SUCCESS_PAGE  = "redirect:" + EXPENSE_SUCCESS_PAGE;
    private static final String EXPENSE_CATEGORIES_PAGE = "menu/expenseCategories";
    private static final String PAYMENT_METHODS_PAGE = "menu/paymentMethods";

    public String handleExpensePage(Model model) {
        expenseTemplateService.addPaymentMethodsAttribute(model);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return EXPENSE_PAGE;
    }

    public String handleExpenseSuccessPage(Model model) {
        expenseTemplateService.addExpenseSuccessAttribute(model);
        return handleExpensePage(model);
    }

    public String handleExpensePageAfterSubmit(BindingResult bindingResult, Model model, ExpenseDTO expenseDTO) {
        expenseTemplateService.addPaymentMethodsAttribute(model);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        if (bindingResult.hasErrors()) {
            return EXPENSE_PAGE;
        }
        expenseService.addExpense(expenseDTO);
        return REDIRECT_EXPENSE_SUCCESS_PAGE;
    }

    public String handleExpenseCategoriesPage(Model model) {
        return EXPENSE_CATEGORIES_PAGE;
    }

    public String handlePaymentMethodsPage(Model model) {
        return PAYMENT_METHODS_PAGE;
    }


}

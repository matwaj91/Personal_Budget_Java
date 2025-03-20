package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseNewPaymentMethodDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class ExpensePaymentMethodPageHandler {

    private final ExpenseService expenseService;
    private final ExpenseTemplateService expenseTemplateService;
    private static final String PAYMENT_METHODS_PAGE = "menu/paymentMethods";
    private static final String NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE  = "addition/success";
    private static final String REDIRECT_NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE  = "redirect:" + NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE;
    private static final String EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE  = "deletion/success";
    private static final String REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE = "redirect:" + EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE;
    private static final String EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE  = "deletion/failure";
    private static final String REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE  = "redirect:" + EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE;
    private static final String EXPENSE_PAYMENT_METHOD_FAILURE_PAGE  = "addition/failure";
    private static final String REDIRECT_EXPENSE_PAYMENT_METHOD_FAILURE_PAGE  = "redirect:" + EXPENSE_PAYMENT_METHOD_FAILURE_PAGE;

    public String handleExpensePaymentMethodsPage(Model model) {
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return PAYMENT_METHODS_PAGE;
    }

    public String handleExpensePaymentMethodPageAfterSubmit(BindingResult bindingResult, Model model,
                                                         @Valid ExpenseNewPaymentMethodDTO expenseNewPaymentMethodDTO) {
        if (bindingResult.hasErrors()) {
            return PAYMENT_METHODS_PAGE;
        }
        if(expenseService.checkIfPaymentMethodNameAlreadyExists(expenseNewPaymentMethodDTO)) {
            return REDIRECT_EXPENSE_PAYMENT_METHOD_FAILURE_PAGE;
        }
        expenseService.addExpensePaymentMethod(expenseNewPaymentMethodDTO);
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return REDIRECT_NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE;
    }

    public String handleExpensePaymentMethodSuccessPage(Model model) {
        expenseTemplateService.addExpensePaymentMethodSuccessAttribute(model);
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return PAYMENT_METHODS_PAGE;
    }

    public String handleExpenseCategoriesFailurePage(Model model) {
        expenseTemplateService.addExpensePaymentMethodFailureAttribute(model);
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return PAYMENT_METHODS_PAGE;
    }

    public String handleExpensePaymentMethodDeletionPageAfterSubmit(BindingResult bindingResult, Model model,
                                                                 @Valid ExpensePaymentMethodDTO expensePaymentMethodDTO) {
        if (bindingResult.hasErrors()) {
            return PAYMENT_METHODS_PAGE;
        }
        if(expenseService.checkIfExpensePaymentMethodsStored(expensePaymentMethodDTO)) {
            return REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE;
        }
        expenseService.deleteExpensePaymentMethod(expensePaymentMethodDTO);
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE;
    }

    public String handleExpensePaymentMethodDeletionSuccessPage(Model model) {
        expenseTemplateService.addExpensePaymentMethodDeletionSuccessAttribute(model);
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return PAYMENT_METHODS_PAGE;
    }

    public String handleExpensePaymentMethodDeletionFailurePage(Model model) {
        expenseTemplateService.addExpensePaymentMethodDeletionFailureAttribute(model);
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return PAYMENT_METHODS_PAGE;
    }

    public String handleExpensePaymentMethodFailurePage(Model model) {
        expenseTemplateService.addExpensePaymentMethodFailureAttribute(model);
        expenseTemplateService.addPaymentMethodsAttribute(model);
        return PAYMENT_METHODS_PAGE;
    }
}

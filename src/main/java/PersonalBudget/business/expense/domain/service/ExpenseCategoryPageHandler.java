package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpenseNewCategoryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class ExpenseCategoryPageHandler {

    private final ExpenseService expenseService;
    private final ExpenseTemplateService expenseTemplateService;
    private static final String EXPENSE_CATEGORIES_PAGE = "menu/expenseCategories";
    private static final String NEW_EXPENSE_CATEGORY_SUCCESS_PAGE  = "addition/success";
    private static final String REDIRECT_NEW_EXPENSE_CATEGORY_SUCCESS_PAGE  = "redirect:" + NEW_EXPENSE_CATEGORY_SUCCESS_PAGE;
    private static final String EXPENSE_CATEGORY_DELETION_SUCCESS_PAGE  = "deletion/success";
    private static final String REDIRECT_EXPENSE_CATEGORY_DELETION_SUCCESS_PAGE = "redirect:" + EXPENSE_CATEGORY_DELETION_SUCCESS_PAGE;
    private static final String EXPENSE_CATEGORY_DELETION_FAILURE_PAGE  = "deletion/failure";
    private static final String REDIRECT_EXPENSE_CATEGORY_DELETION_FAILURE_PAGE  = "redirect:" + EXPENSE_CATEGORY_DELETION_FAILURE_PAGE;
    private static final String EXPENSE_CATEGORY_FAILURE_PAGE  = "addition/failure";
    private static final String REDIRECT_EXPENSE_CATEGORY_FAILURE_PAGE  = "redirect:" + EXPENSE_CATEGORY_FAILURE_PAGE;
    private static final String EXPENSE_CATEGORY_LIMIT_SUCCESS_PAGE  = "spending-limit/success";
    private static final String REDIRECT_EXPENSE_CATEGORY_LIMIT_SUCCESS_PAGE = "redirect:" + EXPENSE_CATEGORY_LIMIT_SUCCESS_PAGE;

    //private static final String PAYMENT_METHODS_PAGE = "menu/paymentMethods";

    public String handleExpenseCategoriesPage(Model model) {
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return EXPENSE_CATEGORIES_PAGE;
    }

    public String handleExpenseCategoriesPageAfterSubmit(BindingResult bindingResult, Model model,
                                                         @Valid ExpenseNewCategoryDTO expenseNewCategoryDTO) {
        if (bindingResult.hasErrors()) {
            return EXPENSE_CATEGORIES_PAGE;
        }
        if(expenseService.checkIfCategoryNameAlreadyExists(expenseNewCategoryDTO)) {
            return REDIRECT_EXPENSE_CATEGORY_FAILURE_PAGE;
        }
        expenseService.addExpenseCategory(expenseNewCategoryDTO);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return REDIRECT_NEW_EXPENSE_CATEGORY_SUCCESS_PAGE;
    }

    public String handleExpenseCategoriesSuccessPage(Model model) {
        expenseTemplateService.addExpenseCategoriesSuccessAttribute(model);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return EXPENSE_CATEGORIES_PAGE;
    }

    public String handleExpenseCategoriesFailurePage(Model model) {
        expenseTemplateService.addExpenseCategoriesFailureAttribute(model);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return EXPENSE_CATEGORIES_PAGE;
    }

    public String handleExpenseDeletionCategoriesPageAfterSubmit(BindingResult bindingResult, Model model,
                                                                 @Valid ExpenseCategoryDTO expenseCategoryDTO) {
        if (bindingResult.hasErrors()) {
            return EXPENSE_CATEGORIES_PAGE;
        }
        if(expenseService.checkIfExpenseCategoriesStored(expenseCategoryDTO)) {
            return REDIRECT_EXPENSE_CATEGORY_DELETION_FAILURE_PAGE;
        }
        expenseService.deleteExpenseCategory(expenseCategoryDTO);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return REDIRECT_EXPENSE_CATEGORY_DELETION_SUCCESS_PAGE;
    }

    public String handleExpenseCategoriesDeletionSuccessPage(Model model) {
        expenseTemplateService.addExpenseCategoriesDeletionSuccessAttribute(model);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return EXPENSE_CATEGORIES_PAGE;
    }

    public String handleExpenseCategoriesDeletionFailurePage(Model model) {
        expenseTemplateService.addExpenseCategoriesDeletionFailureAttribute(model);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return EXPENSE_CATEGORIES_PAGE;
    }

    public String handleSpendingLimitPageAfterSubmit(BindingResult bindingResult, Model model,
                                                     @Valid ExpenseCategoryDTO expenseCategoryDTO) {
        if (bindingResult.hasErrors()) {
            return EXPENSE_CATEGORIES_PAGE;
        }
        expenseService.setSpendingLimit(expenseCategoryDTO);
        return REDIRECT_EXPENSE_CATEGORY_LIMIT_SUCCESS_PAGE;
    }

    public String handleSettingLimitSuccessPage(Model model) {
        expenseTemplateService.addSettingLimitSuccessAttribute(model);
        expenseTemplateService.addExpenseCategoriesAttribute(model);
        return EXPENSE_CATEGORIES_PAGE;
    }
}

package PersonalBudget.business.expense.domain.controller;

import PersonalBudget.business.expense.domain.service.ExpenseCategoryPageHandler;
import PersonalBudget.business.expense.domain.service.ExpensePageHandler;
import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import PersonalBudget.business.expense.dto.ExpenseNewCategoryDTO;
import PersonalBudget.common.util.Request;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/expense")
public class ExpenseController {

    private final ExpensePageHandler expensePageHandler;
    private final ExpenseCategoryPageHandler expenseCategoryPageHandler;

    @GetMapping()
    public String getExpensePage(Model model) {
        return expensePageHandler.handleExpensePage(model);
    }

    @ModelAttribute("expenseDTO")
    public ExpenseDTO expenseDTO(BigDecimal amount, LocalDate expenseDate, Long paymentMethodId, Long expenseCategoryId, String expenseComment) {
        return new ExpenseDTO(amount, expenseDate, paymentMethodId, expenseCategoryId, expenseComment);
    }

    @PostMapping(value = "/selectedExpenseCategory")
    @ResponseBody
    public Map<String, Object> getResponseAfterSelectingExpenseCategoryOption(@RequestBody Request request) {
        Long selectedExpenseCategory = request.getOption();
        BigDecimal expenseSum = expensePageHandler.handleExpensePageAfterSelectingCategory(selectedExpenseCategory);
        Map<String, Object> response = new HashMap<>();
        response.put("expenseSum", expenseSum);
        return response;
    }

    @PostMapping()
    public String getExpensePageAfterSubmit(@Valid @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO,
                                            BindingResult bindingResult, Model model) {
        return expensePageHandler.handleExpensePageAfterSubmit(bindingResult, model, expenseDTO);
    }

    @GetMapping(value = "/success")
    public String getExpenseSuccessPage(Model model) {
        return expensePageHandler.handleExpenseSuccessPage(model);
    }

    @GetMapping(value = "/expense-categories")
    public String getExpenseCategoriesPage(Model model) {
        return expenseCategoryPageHandler.handleExpenseCategoriesPage(model);
    }

    @ModelAttribute("expenseNewCategoryDTO")
    public ExpenseNewCategoryDTO expenseNewCategoryDTO(String name) {
        return new ExpenseNewCategoryDTO(name);
    }

    @PostMapping(value = "/expense-categories/addition")
    public String getProperPageAfterAddingNewExpenseCategory(@Valid @ModelAttribute("expenseNewCategoryDTO") ExpenseNewCategoryDTO expenseNewCategoryDTO,
                                                            BindingResult bindingResult, Model model) {
        return expenseCategoryPageHandler.handleExpenseCategoriesPageAfterSubmit(bindingResult, model, expenseNewCategoryDTO);
    }

    @GetMapping(value = "/expense-categories/addition/success")
    public String getExpenseCategorySuccessPage(Model model) {
        return expenseCategoryPageHandler.handleExpenseCategoriesSuccessPage(model);
    }

    @GetMapping(value = "/expense-categories/addition/failure")
    public String getExpenseCategoryFailurePage(Model model) {
        return expenseCategoryPageHandler.handleExpenseCategoriesFailurePage(model);
    }

    @ModelAttribute("expenseCategoryDTO")
    public ExpenseCategoryDTO expenseNewCategoryDTO(Long id, String expenseCategory, BigDecimal limitAmount) {
        return new ExpenseCategoryDTO(id, expenseCategory, limitAmount);
    }

    @PostMapping(value = "/expense-categories/deletion")
    public String getProperPageAfterDeletingExpenseCategory(@Valid @ModelAttribute("expenseCategoryDTO") ExpenseCategoryDTO expenseCategoryDTO,
                                                           BindingResult bindingResult, Model model) {
        return expenseCategoryPageHandler.handleExpenseDeletionCategoriesPageAfterSubmit(bindingResult, model, expenseCategoryDTO);
    }

    @GetMapping(value = "/expense-categories/deletion/success")
    public String getExpenseCategoryDeletionSuccessPage(Model model) {
        return expenseCategoryPageHandler.handleExpenseCategoriesDeletionSuccessPage(model);
    }

    @GetMapping(value = "/expense-categories/deletion/failure")
    public String getExpenseCategoryDeletionFailurePage(Model model) {
        return expenseCategoryPageHandler.handleExpenseCategoriesDeletionFailurePage(model);
    }

    @PostMapping(value = "/spending-limit")
    public String getProperPageAfterSettingSpendingLimit(@Valid @ModelAttribute("expenseCategoryLimitDTO") ExpenseCategoryDTO expenseCategoryDTO,
                                                            BindingResult bindingResult, Model model) {
        return expenseCategoryPageHandler.handleSpendingLimitPageAfterSubmit(bindingResult, model, expenseCategoryDTO);
    }

    @GetMapping(value = "/spending-limit/success")
    public String getSettingLimitSuccessPage(Model model) {
        return expenseCategoryPageHandler.handleSettingLimitSuccessPage(model);
    }

    //    @GetMapping(value = "/payment-methods")
//    public String getPaymentMethodsPage(Model model) {
//        return expenseCategoryPageHandler.handlePaymentMethodsPage(model);
//    }
}

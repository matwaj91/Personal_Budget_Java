package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.balance.domain.service.BalanceTemplateService;
import PersonalBudget.business.expense.dto.ExpenseCategoryDTO;
import PersonalBudget.business.expense.dto.ExpenseNewCategoryDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseCategoryPageHandlerTest {

    private final ExpenseNewCategoryDTO expenseNewCategoryDTO = new ExpenseNewCategoryDTO("education");
    private final ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO(1L, "food", new BigDecimal(100));
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

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ExpenseTemplateService expenseTemplateService;

    @Mock
    private BalanceTemplateService balanceTemplateService;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseCategoryPageHandler expenseCategoryPageHandler;

    @Test
    void handleExpenseCategoriesPageTest() {
        String result = expenseCategoryPageHandler.handleExpenseCategoriesPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleExpenseCategoriesPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = expenseCategoryPageHandler.handleExpenseCategoriesPageAfterSubmit(bindingResult, model, expenseNewCategoryDTO);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleExpenseCategoriesPageAfterSubmitTest() {
        String result = expenseCategoryPageHandler.handleExpenseCategoriesPageAfterSubmit(bindingResult, model, expenseNewCategoryDTO);

        assertTrue(result.contains(REDIRECT_NEW_EXPENSE_CATEGORY_SUCCESS_PAGE));
    }

    @Test
    void handleExpenseCategoriesPageNameAlreadyExist() {
        when(expenseService.checkIfCategoryNameAlreadyExists(expenseNewCategoryDTO)).thenReturn(true);

        String result = expenseCategoryPageHandler.handleExpenseCategoriesPageAfterSubmit(bindingResult, model, expenseNewCategoryDTO);

        assertTrue(result.contains(REDIRECT_EXPENSE_CATEGORY_FAILURE_PAGE));
    }

    @Test
    void handleExpenseCategoriesSuccessPage() {
        String result = expenseCategoryPageHandler.handleExpenseCategoriesSuccessPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleExpenseCategoriesFailurePage() {
        String result = expenseCategoryPageHandler.handleExpenseCategoriesFailurePage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleExpenseDeletionCategoriesPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = expenseCategoryPageHandler.handleExpenseDeletionCategoriesPageAfterSubmit(bindingResult, model, expenseCategoryDTO);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleExpenseDeletionCategoriesPageCategoryAlreadyExists() {
        when(expenseService.checkIfExpenseCategoriesStored(expenseCategoryDTO)).thenReturn(true);

        String result = expenseCategoryPageHandler.handleExpenseDeletionCategoriesPageAfterSubmit(bindingResult, model, expenseCategoryDTO);

        assertTrue(result.contains(REDIRECT_EXPENSE_CATEGORY_DELETION_FAILURE_PAGE));
    }

    @Test
    void handleExpenseDeletionCategoriesPageTest() {
        String result = expenseCategoryPageHandler.handleExpenseDeletionCategoriesPageAfterSubmit(bindingResult, model, expenseCategoryDTO);

        assertTrue(result.contains(REDIRECT_EXPENSE_CATEGORY_DELETION_SUCCESS_PAGE));
    }

    @Test
    void handleExpenseCategoriesDeletionSuccessPage() {
        String result = expenseCategoryPageHandler.handleExpenseCategoriesDeletionSuccessPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleExpenseCategoriesDeletionFailurePage() {
        String result = expenseCategoryPageHandler.handleExpenseCategoriesDeletionFailurePage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleSpendingLimitPageHasErrors() {
        ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO(1L, "food", new BigDecimal(100));
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = expenseCategoryPageHandler.handleSpendingLimitPageAfterSubmit(bindingResult, model, expenseCategoryDTO);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void handleSpendingLimitPageAfterSubmitTest() {
        String result = expenseCategoryPageHandler.handleSpendingLimitPageAfterSubmit(bindingResult, model, expenseCategoryDTO);

        assertEquals(REDIRECT_EXPENSE_CATEGORY_LIMIT_SUCCESS_PAGE, result);
    }

    @Test
    void handleSettingLimitSuccessPage() {
        String result = expenseCategoryPageHandler.handleSettingLimitSuccessPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }
}
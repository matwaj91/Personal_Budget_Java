package PersonalBudget.business.expense.domain.controller;

import PersonalBudget.business.expense.domain.service.*;
import PersonalBudget.business.expense.dto.*;
import PersonalBudget.common.util.Request;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    private final Long PAYMENT_METHOD_ID = 1L;
    private final Long EXPENSE_CATEGORY_ID = 2L;
    private final String EXPENSE_COMMENT = " ";
    private final BigDecimal AMOUNT = new BigDecimal(100);
    private final BigDecimal LIMIT_AMOUNT = new BigDecimal(100);
    private final ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO(
            EXPENSE_CATEGORY_ID, TEST_CATEGORY, LIMIT_AMOUNT);
    private final LocalDate EXPENSE_DATE = LocalDate.of(2015, 10, 10);
    private final String PAYMENT_METHOD = "testPaymentMethod";
    private static final String TEST_CATEGORY = "testCategory";
    private static final String EXPENSE_PAGE = "menu/expense";
    private static final String EXPENSE_SUCCESS_PAGE = "expense/success";
    private static final String PAYMENT_METHODS_PAGE = "menu/paymentMethods";
    private static final String EXPENSE_CATEGORIES_PAGE = "menu/expenseCategories";
    private static final String NEW_EXPENSE_CATEGORY_SUCCESS_PAGE = "addition/success";
    private static final String REDIRECT_NEW_EXPENSE_CATEGORY_SUCCESS_PAGE = "redirect:" +
            NEW_EXPENSE_CATEGORY_SUCCESS_PAGE;
    private static final String NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE = "addition/success";
    private static final String REDIRECT_NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE = "redirect:" +
            NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE;
    private static final String EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE = "deletion/success";
    private static final String REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE = "redirect:" +
            EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE;


    @Mock
    private Model model;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ExpensePageHandler expensePageHandler;

    @Mock
    private ExpenseTemplateService expenseTemplateService;

    @Mock
    private ExpenseCategoryPageHandler expenseCategoryPageHandler;

    @Mock
    private ExpensePaymentMethodPageHandler expensePaymentMethodPageHandler;

    @InjectMocks
    private ExpenseController expenseController;

    @Test
    void getExpensePageTest() {
        when(expensePageHandler.handleExpensePage(model)).thenReturn(EXPENSE_PAGE);

        String result = expenseController.getExpensePage(model);

        assertEquals(EXPENSE_PAGE, result);
    }

    @Test
    void expenseDTOTest() {
        ExpenseDTO result = expenseController.expenseDTO(
                AMOUNT, EXPENSE_DATE, PAYMENT_METHOD_ID, EXPENSE_CATEGORY_ID, EXPENSE_COMMENT);

        assertEquals(AMOUNT, result.amount());
        assertEquals(PAYMENT_METHOD_ID, result.paymentMethodId());
    }

    @Test
    public void getResponseAfterSelectingExpenseCategoryOptionTest() {
        Request requestBody = new Request();
        requestBody.setOption(1L);

        Map<String, Object> result = expenseController.getResponseAfterSelectingExpenseCategoryOption(requestBody);

        assertNotNull(result);
    }

    @Test
    void getExpensePageAfterSubmitTest() {
        ExpenseDTO expenseDTO = new ExpenseDTO(
                AMOUNT, EXPENSE_DATE, PAYMENT_METHOD_ID, EXPENSE_CATEGORY_ID, EXPENSE_COMMENT);

        when(expensePageHandler.handleExpensePageAfterSubmit(bindingResult, model, expenseDTO)).
                thenReturn(EXPENSE_SUCCESS_PAGE);

        String result = expenseController.getExpensePageAfterSubmit(expenseDTO, bindingResult, model);

        assertEquals(EXPENSE_SUCCESS_PAGE, result);
    }

    @Test
    void getExpenseSuccessPageTest() {
        when(expensePageHandler.handleExpenseSuccessPage(model)).thenReturn(EXPENSE_PAGE);

        String result = expenseController.getExpenseSuccessPage(model);

        assertEquals(EXPENSE_PAGE, result);
    }

    @Test
    void getExpenseCategoriesPageTest() {
        when(expenseCategoryPageHandler.handleExpenseCategoriesPage(model)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getExpenseCategoriesPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void expenseNewCategoryDTOTest() {
        ExpenseNewCategoryDTO result = expenseController.expenseNewCategoryDTO(TEST_CATEGORY);

        assertEquals(TEST_CATEGORY, result.name());
    }

    @Test
    void getProperPageAfterAddingNewExpenseCategoryTest() {
        ExpenseNewCategoryDTO expenseNewCategoryDTO = expenseController.expenseNewCategoryDTO(TEST_CATEGORY);

        when(expenseCategoryPageHandler.handleExpenseCategoriesPageAfterSubmit(bindingResult, model, expenseNewCategoryDTO)).
                thenReturn(REDIRECT_NEW_EXPENSE_CATEGORY_SUCCESS_PAGE);

        String result = expenseController.getProperPageAfterAddingNewExpenseCategory(
                expenseNewCategoryDTO, bindingResult, model);

        assertEquals(REDIRECT_NEW_EXPENSE_CATEGORY_SUCCESS_PAGE, result);
    }

    @Test
    void getExpenseCategorySuccessPage() {
        when(expenseCategoryPageHandler.handleExpenseCategoriesSuccessPage(model)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getExpenseCategorySuccessPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void getExpenseCategoryFailurePageTest() {
        when(expenseCategoryPageHandler.handleExpenseCategoriesFailurePage(model)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getExpenseCategoryFailurePage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void ExpenseNewCategoryDTOTest() {
        ExpenseCategoryDTO result = expenseController.expenseNewCategoryDTO(
                EXPENSE_CATEGORY_ID, TEST_CATEGORY, LIMIT_AMOUNT);

        assertEquals(TEST_CATEGORY, result.expenseCategory());
    }

    @Test
    void getProperPageAfterDeletingExpenseCategoryTest() {
        when(expenseCategoryPageHandler.handleExpenseDeletionCategoriesPageAfterSubmit(bindingResult, model, expenseCategoryDTO)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getProperPageAfterDeletingExpenseCategory(expenseCategoryDTO, bindingResult, model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void getExpenseCategoryDeletionSuccessPageTest() {
        when(expenseCategoryPageHandler.handleExpenseCategoriesDeletionSuccessPage(model)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getExpenseCategoryDeletionSuccessPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void getExpenseCategoryDeletionFailurePageTest() {
        when(expenseCategoryPageHandler.handleExpenseCategoriesDeletionFailurePage(model)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getExpenseCategoryDeletionFailurePage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void getProperPageAfterSettingSpendingLimitTest() {
        when(expenseCategoryPageHandler.handleSpendingLimitPageAfterSubmit(bindingResult, model, expenseCategoryDTO)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getProperPageAfterSettingSpendingLimit(expenseCategoryDTO, bindingResult, model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void getSettingLimitSuccessPageTest() {
        when(expenseCategoryPageHandler.handleSettingLimitSuccessPage(model)).thenReturn(EXPENSE_CATEGORIES_PAGE);

        String result = expenseController.getSettingLimitSuccessPage(model);

        assertEquals(EXPENSE_CATEGORIES_PAGE, result);
    }

    @Test
    void getPaymentMethodsPageTest() {
        when(expensePaymentMethodPageHandler.handleExpensePaymentMethodsPage(model)).thenReturn(PAYMENT_METHODS_PAGE);

        String result = expenseController.getPaymentMethodsPage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void expenseNewPaymentMethodDTOTest() {
        ExpenseNewPaymentMethodDTO result = expenseController.expenseNewPaymentMethodDTO(PAYMENT_METHOD);

        assertEquals(PAYMENT_METHOD, result.name());
    }

    @Test
    void getProperPageAfterAddingNewExpensePaymentMethodTest() {
        ExpenseNewPaymentMethodDTO expenseNewPaymentMethodDTO = new ExpenseNewPaymentMethodDTO(PAYMENT_METHOD);
        when(expensePaymentMethodPageHandler.handleExpensePaymentMethodsPage(model)).thenReturn(REDIRECT_NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE);

        expenseController.getProperPageAfterAddingNewExpensePaymentMethod(expenseNewPaymentMethodDTO, bindingResult, model);
    }

    @Test
    void getExpensePaymentMethodSuccessPageTest() {
        when(expensePaymentMethodPageHandler.handleExpensePaymentMethodSuccessPage(model)).thenReturn(PAYMENT_METHODS_PAGE);

        String result = expenseController.getExpensePaymentMethodSuccessPage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void getExpensePaymentMethodFailurePageTest() {
        when(expensePaymentMethodPageHandler.handleExpensePaymentMethodFailurePage(model)).thenReturn(PAYMENT_METHODS_PAGE);

        String result = expenseController.getExpensePaymentMethodFailurePage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void expensePaymentMethodDTOTest() {
        ExpensePaymentMethodDTO result = expenseController.expensePaymentMethodDTO(PAYMENT_METHOD_ID, PAYMENT_METHOD);

        assertEquals(PAYMENT_METHOD, result.expensePaymentMethod());
    }

    @Test
    void getProperPageAfterDeletingExpensePaymentMethodTest() {
        ExpensePaymentMethodDTO expensePaymentMethodDTO = new ExpensePaymentMethodDTO(PAYMENT_METHOD_ID, PAYMENT_METHOD);
        when(expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionPageAfterSubmit(bindingResult, model, expensePaymentMethodDTO)).thenReturn(REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE);

        String result = expenseController.getProperPageAfterDeletingExpensePaymentMethod(expensePaymentMethodDTO, bindingResult, model);

        assertEquals(REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE, result);
    }

    @Test
    void getExpensePaymentMethodDeletionSuccessPageTest() {
        when(expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionSuccessPage(model)).thenReturn(PAYMENT_METHODS_PAGE);

        String result = expenseController.getExpensePaymentMethodDeletionSuccessPage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void getExpensePaymentMethodDeletionFailurePageTest() {
        when(expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionFailurePage(model)).thenReturn(PAYMENT_METHODS_PAGE);

        String result = expenseController.getExpensePaymentMethodDeletionFailurePage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }
}
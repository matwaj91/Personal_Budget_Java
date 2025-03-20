package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseNewPaymentMethodDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExpensePaymentMethodPageHandlerTest {

    private final ExpensePaymentMethodDTO expensePaymentMethodDTO = new ExpensePaymentMethodDTO(1L, "cash");
    private final ExpenseNewPaymentMethodDTO expenseNewPaymentMethodDTO = new ExpenseNewPaymentMethodDTO("phone");
    private static final String PAYMENT_METHODS_PAGE = "menu/paymentMethods";
    private static final String NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE  = "addition/success";
    private static final String REDIRECT_NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE  = "redirect:" + NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE;
    private static final String EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE  = "deletion/success";
    private static final String REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE = "redirect:" + EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE;
    private static final String EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE  = "deletion/failure";
    private static final String REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE  = "redirect:" + EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE;
    private static final String EXPENSE_PAYMENT_METHOD_FAILURE_PAGE  = "addition/failure";
    private static final String REDIRECT_EXPENSE_PAYMENT_METHOD_FAILURE_PAGE  = "redirect:" + EXPENSE_PAYMENT_METHOD_FAILURE_PAGE;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private ExpenseTemplateService expenseTemplateService;

    @InjectMocks
    private ExpensePaymentMethodPageHandler expensePaymentMethodPageHandler;

    @Test
    void handleExpensePaymentMethodsPageTest() {
        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodsPage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodPageAfterSubmit(bindingResult, model, expenseNewPaymentMethodDTO);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodPageCheckIfPaymentsAlreadyExist() {
        when(expenseService.checkIfPaymentMethodNameAlreadyExists(expenseNewPaymentMethodDTO)).thenReturn(true);

        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodPageAfterSubmit(bindingResult, model, expenseNewPaymentMethodDTO);

        assertEquals(REDIRECT_EXPENSE_PAYMENT_METHOD_FAILURE_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodPageAfterSubmitTest() {
        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodPageAfterSubmit(bindingResult, model, expenseNewPaymentMethodDTO);

        assertEquals(REDIRECT_NEW_EXPENSE_PAYMENT_METHOD_SUCCESS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodSuccessPageTest() {
        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodSuccessPage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void handleExpenseCategoriesFailurePageTest() {
        String result = expensePaymentMethodPageHandler.handleExpenseCategoriesFailurePage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodDeletionPageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionPageAfterSubmit(bindingResult, model, expensePaymentMethodDTO);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodDeletionPageCheckIfExpensePaymentMethodsStored() {
        when(expenseService.checkIfExpensePaymentMethodsStored(expensePaymentMethodDTO)).thenReturn(true);

        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionPageAfterSubmit(bindingResult, model, expensePaymentMethodDTO);

        assertEquals(REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_FAILURE_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodDeletionPageAfterSubmit() {
        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionPageAfterSubmit(bindingResult, model, expensePaymentMethodDTO);

        assertEquals(REDIRECT_EXPENSE_PAYMENT_METHOD_DELETION_SUCCESS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodDeletionSuccessPageTest() {
        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionSuccessPage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodDeletionFailurePageTest() {
        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodDeletionFailurePage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }

    @Test
    void handleExpensePaymentMethodFailurePageTest() {
        String result = expensePaymentMethodPageHandler.handleExpensePaymentMethodFailurePage(model);

        assertEquals(PAYMENT_METHODS_PAGE, result);
    }
}
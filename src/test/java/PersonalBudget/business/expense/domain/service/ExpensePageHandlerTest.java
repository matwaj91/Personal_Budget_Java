package PersonalBudget.business.expense.domain.service;

import PersonalBudget.business.expense.dto.ExpenseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ExpensePageHandlerTest {

    private final ExpenseDTO expenseDTO = new ExpenseDTO(
            new BigDecimal(100),
            LocalDate.of(2015, Month.JANUARY, 13),
            1L,
            4L,
            " ");
    private static final String EXPENSE_PAGE = "menu/expense";
    private static final String EXPENSE_SUCCESS_PAGE = "expense/success";
    private static final String REDIRECT_EXPENSE_SUCCESS_PAGE = "redirect:" + EXPENSE_SUCCESS_PAGE;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ExpenseService expenseService;

    @Mock
    private ExpenseTemplateService expenseTemplateService;

    @InjectMocks
    private ExpensePageHandler expensePageHandler;

    @Test
    void handleExpensePageTest() {
        String result = expensePageHandler.handleExpensePage(model);

        assertEquals(EXPENSE_PAGE, result);
    }

    @Test
    void handleExpenseSuccessPageTest() {
        String result = expensePageHandler.handleExpenseSuccessPage(model);

        assertEquals(EXPENSE_PAGE, result);
    }

    @Test
    void handleExpensePageHasErrorTest() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = expensePageHandler.handleExpensePageAfterSubmit(bindingResult, model, expenseDTO);

        assertEquals(EXPENSE_PAGE, result);
    }

    @Test
    void handleExpensePageAfterSubmitTest() {
        String result = expensePageHandler.handleExpensePageAfterSubmit(bindingResult, model, expenseDTO);

        assertEquals(REDIRECT_EXPENSE_SUCCESS_PAGE, result);
    }

    @Test
    void handleExpensePageAfterSelectingCategoryTest() {
        Long selectedExpenseCategory = 2L;
        BigDecimal expectedOutput = new BigDecimal(50);
        when(expenseService.getCurrentMonthExpenseSum(selectedExpenseCategory)).thenReturn(expectedOutput);

        BigDecimal result = expensePageHandler.handleExpensePageAfterSelectingCategory(selectedExpenseCategory);

        assertEquals(expectedOutput, result);

    }
}
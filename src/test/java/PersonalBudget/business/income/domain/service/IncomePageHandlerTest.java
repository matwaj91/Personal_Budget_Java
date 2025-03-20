package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.dto.IncomeDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class IncomePageHandlerTest {

    private final IncomeDTO incomeDTO = new IncomeDTO(
            new BigDecimal(1000),
            LocalDate.of(2015, Month.JANUARY, 22),
            3L,
            " ");
    private static final String INCOME_PAGE = "menu/income";
    private static final String INCOME_SUCCESS_PAGE = "income/success";
    private static final String REDIRECT_INCOME_SUCCESS_PAGE  = "redirect:" + INCOME_SUCCESS_PAGE;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private IncomeService incomeService;

    @Mock
    private IncomeTemplateService incomeTemplateService;

    @InjectMocks
    private IncomePageHandler incomePageHandler;

    @Test
    void handleIncomePageTest() {
        String result = incomePageHandler.handleIncomePage(model);

        assertEquals(INCOME_PAGE, result);
    }

    @Test
    void handleIncomePageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = incomePageHandler.handleIncomePageAfterSubmit(bindingResult, model, incomeDTO);

        assertEquals(INCOME_PAGE, result);
    }

    @Test
    void handleIncomePageAfterSubmitTest() {
        String result = incomePageHandler.handleIncomePageAfterSubmit(bindingResult, model, incomeDTO);

        assertEquals(REDIRECT_INCOME_SUCCESS_PAGE, result);
    }

    @Test
    void handleIncomeSuccessPageTest() {
        String result = incomePageHandler.handleIncomeSuccessPage(model);

        assertEquals(INCOME_PAGE, result);
    }
}
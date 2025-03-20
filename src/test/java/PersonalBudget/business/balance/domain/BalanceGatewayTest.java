package PersonalBudget.business.balance.domain;

import PersonalBudget.business.expense.domain.service.ExpenseService;
import PersonalBudget.business.income.domain.service.IncomeService;
import PersonalBudget.common.util.ParticularActivityDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceGatewayTest {

    private final LocalDate dateFrom = LocalDate.of(2010, Month.JANUARY, 20);
    private final LocalDate dateTo = LocalDate.of(2010, Month.JANUARY, 25);
    private final List<ParticularActivityDTO> expectedOutput = List.of(
            new ParticularActivityDTO(
                    new BigDecimal(100),
                    LocalDate.of(2010, Month.JANUARY, 20),
                    "salary"));

    @InjectMocks
    private BalanceGateway balanceGateway;

    @Mock
    private IncomeService incomeService;

    @Mock
    private ExpenseService expenseService;

    @Test
    void fetchUserParticularsIncomeCategoryTest() {
        when(incomeService.getUserParticularsIncomeCategory(dateFrom, dateTo)).thenReturn(expectedOutput);

        List<ParticularActivityDTO> result = balanceGateway.fetchUserParticularsIncomeCategory(dateFrom, dateTo);

        assertEquals(expectedOutput, result);
    }

    @Test
    void fetchUserParticularsExpenseCategoryTest() {
        when(expenseService.getUserParticularsExpenseCategory(dateFrom, dateTo)).thenReturn(expectedOutput);

        List<ParticularActivityDTO> result = balanceGateway.fetchUserParticularsExpenseCategory(dateFrom, dateTo);

        assertEquals(expectedOutput, result);
    }
}
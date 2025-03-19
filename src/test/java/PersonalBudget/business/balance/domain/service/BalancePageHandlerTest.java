package PersonalBudget.business.balance.domain.service;

import PersonalBudget.common.util.TimePeriodDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.Month;

import static PersonalBudget.common.util.DateUtils.getFirstDayCurrentMonth;
import static PersonalBudget.common.util.DateUtils.getLastDayCurrentMonth;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BalancePageHandlerTest {

    private static final String BALANCE_PAGE = "menu/balance";
    private static final String MENU_PAGE = "menu/main";

    @Mock
    private Model model;

    @Mock
    private BalanceService balanceService;

    @Mock
    private BalanceTemplateService balanceTemplateService;

    @InjectMocks
    private BalancePageHandler balancePageHandler;

    @Test
    public void handleCurrentMonthBalancePageTest() {
        String result = balancePageHandler.handleCurrentMonthBalancePage(model);

        assertEquals(BALANCE_PAGE, result);
    }

    @Test
    public void handlePreviousMonthBalancePageTest() {
        String result = balancePageHandler.handlePreviousMonthBalancePage(model);

        assertEquals(BALANCE_PAGE, result);
    }

    @Test
    public void handleCurrentYearBalancePageTest() {
        String result = balancePageHandler.handleCurrentYearBalancePage(model);

        assertEquals(BALANCE_PAGE, result);
    }

    @Test
    public void handleTimeRangeBalancePageWithProperDate() {
        LocalDate dateFrom = LocalDate.of(2020, Month.JANUARY, 10);
        LocalDate dateTo = LocalDate.of(2020, Month.JANUARY, 15);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(dateFrom, dateTo);

        String result = balancePageHandler.handleTimeRangeBalancePage(timePeriodDTO, model);

        assertEquals(BALANCE_PAGE, result);
    }

    @Test
    public void handleTimeRangeBalancePageWithImproperDate() {
        LocalDate dateFrom = LocalDate.of(2020, Month.JANUARY, 10);
        LocalDate dateTo = LocalDate.of(2020, Month.JANUARY, 5);
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(dateFrom, dateTo);

        String result = balancePageHandler.handleTimeRangeBalancePage(timePeriodDTO, model);

        assertEquals(MENU_PAGE, result);
    }

    @Test
    public void handleTimeRangeBalancePageWithoutDateRange() {
        String result = balancePageHandler.handleTimeRangeBalancePageWithoutDateRange(model);

        assertEquals(MENU_PAGE, result);
    }

    @Test
    @WithMockUser(username = "user")
    public void handleBalancePageTest() {
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo= getLastDayCurrentMonth();

        String result = balancePageHandler.handleBalancePage(model, dateFrom, dateTo);

        assertEquals(BALANCE_PAGE, result);
    }
}
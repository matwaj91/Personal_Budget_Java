package PersonalBudget.business.balance.domain.controller;

import PersonalBudget.business.balance.domain.service.BalancePageHandler;
import PersonalBudget.business.balance.domain.service.BalanceTemplateService;
import PersonalBudget.common.util.TimePeriodDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.time.LocalDate;

import static PersonalBudget.common.util.DateUtils.getFirstDayCurrentMonth;
import static PersonalBudget.common.util.DateUtils.getLastDayCurrentMonth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceControllerTest {

    private static final String BALANCE_PAGE = "menu/balance";
    private static final String MENU_PAGE = "menu/main";

    @Mock
    private Model model;

    @Mock
    private BalancePageHandler balancePageHandler;

    @Mock
    private BalanceTemplateService balanceTemplateService;

    @InjectMocks
    private BalanceController balanceController;


    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCurrentMonthBalancePageNoAuthorisationTest() throws Exception {
        mockMvc.perform(get("/api/v1/menu/current-month"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/api/v1/login"));
    }

    @Test
    void getCurrentMonthBalancePageTest()  {
        when(balancePageHandler.handleCurrentMonthBalancePage(model)).thenReturn(BALANCE_PAGE);

        String result = balanceController.getCurrentMonthBalancePage(model);

        assertEquals(BALANCE_PAGE, result);
    }

    @Test
    void getPreviousMonthBalancePageTest() {
        when(balancePageHandler.handlePreviousMonthBalancePage(model)).thenReturn(BALANCE_PAGE);

        String result = balanceController.getPreviousMonthBalancePage(model);

        assertEquals(BALANCE_PAGE, result);
    }

    @Test
    void getCurrentYearBalancePageTest() {
        when(balancePageHandler.handleCurrentYearBalancePage(model)).thenReturn(BALANCE_PAGE);

        String result = balanceController.getCurrentYearBalancePage(model);

        assertEquals(BALANCE_PAGE, result);
    }

    @Test
    void nonstandardDateDTOTest() {
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo= getLastDayCurrentMonth();

        TimePeriodDTO result = balanceController.nonstandardDateDTO(dateFrom, dateTo);

        assertEquals(dateFrom, result.dateFrom());
        assertEquals(dateTo, result.dateTo());
    }

    @Test
    void getTimeRangeBalancePageTest() {
        LocalDate dateFrom = getFirstDayCurrentMonth();
        LocalDate dateTo= getLastDayCurrentMonth();
        TimePeriodDTO timePeriodDTO = new TimePeriodDTO(dateFrom, dateTo);

        when(balanceController.getTimeRangeBalancePage(timePeriodDTO, model)).thenReturn(MENU_PAGE);

    }

    @Test
    void testGetTimeRangeBalancePageTest() {
        when(balanceController.getTimeRangeBalancePage(model)).thenReturn(MENU_PAGE);
    }
}
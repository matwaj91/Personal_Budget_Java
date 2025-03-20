package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.balance.domain.BalanceGateway;
import PersonalBudget.business.balance.domain.mapper.BalanceMapper;
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
class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceGateway balanceGateway;

    @Mock
    private BalanceMapper balanceMapper;

    @Test
    void getCategoriesSumTest() {
        List<ParticularActivityDTO> particularActivities = List.of(
                new ParticularActivityDTO(
                        new BigDecimal(100),
                        LocalDate.of(2010, Month.JANUARY, 20),
                        "salary"),
                new ParticularActivityDTO(
                        new BigDecimal(100),
                        LocalDate.of(2010, Month.JANUARY, 20),
                        "salary")
        );

        List<List<Object>> expectedOutput = List.of(
                List.of(new ParticularActivityDTO(
                                new BigDecimal(100),
                                LocalDate.of(2010, Month.JANUARY, 20),
                                "salary"),
                        new ParticularActivityDTO(
                                new BigDecimal(100),
                                LocalDate.of(2010, Month.JANUARY, 20),
                                "salary"))
        );

        when(balanceMapper.mapParticularActivityDTOToNameAndTotalAmountList(particularActivities)).thenReturn(expectedOutput);

        List<List<Object>> result = balanceService.getCategoriesSum(particularActivities);

        assertEquals(expectedOutput, result);
    }

    @Test
    void getUserParticularsIncomeCategoryTest() {
        LocalDate dateFrom = LocalDate.of(2010, Month.JANUARY, 20);
        LocalDate dateTo = LocalDate.of(2010, Month.JANUARY, 25);

        List<ParticularActivityDTO> expectedOutput = List.of(
                new ParticularActivityDTO(
                        new BigDecimal(100),
                        LocalDate.of(2010, Month.JANUARY, 20),
                        "salary")
        );

        when(balanceGateway.fetchUserParticularsIncomeCategory(dateFrom, dateTo)).thenReturn(expectedOutput);

        List<ParticularActivityDTO> result = balanceService.getUserParticularsIncomeCategory(dateFrom, dateTo);

        assertEquals(expectedOutput, result);
    }

    @Test
    void getUserParticularsExpensesCategoryTest() {
        LocalDate dateFrom = LocalDate.of(2010, Month.JANUARY, 20);
        LocalDate dateTo = LocalDate.of(2010, Month.JANUARY, 25);

        List<ParticularActivityDTO> expectedOutput = List.of(
                new ParticularActivityDTO(
                        new BigDecimal(100),
                        LocalDate.of(2010, Month.JANUARY, 20),
                        "salary")
        );

        when(balanceGateway.fetchUserParticularsExpenseCategory(dateFrom, dateTo)).thenReturn(expectedOutput);

        List<ParticularActivityDTO> result = balanceService.getUserParticularsExpensesCategory(dateFrom, dateTo);

        assertEquals(expectedOutput, result);
    }

    @Test
    void calculateTotalSumTest() {
        List<ParticularActivityDTO> particularActivities = List.of(
                new ParticularActivityDTO(
                        new BigDecimal(100),
                        LocalDate.of(2010, Month.JANUARY, 20),
                        "salary"),
                new ParticularActivityDTO(
                        new BigDecimal(100),
                        LocalDate.of(2010, Month.JANUARY, 20),
                        "salary")
        );

        BigDecimal result = balanceService.calculateTotalSum(particularActivities);

        assertEquals(result, BigDecimal.valueOf(200));
    }
}
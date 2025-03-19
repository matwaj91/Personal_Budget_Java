package PersonalBudget.business.balance.domain.service;

import PersonalBudget.common.util.ParticularActivityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceTemplateServiceTest {

    @Mock
    private BalanceTemplateService balanceTemplateService;

    @Mock
    private Model model;

    @Mock
    private BalanceService balanceService;

    @BeforeEach
    public void setUp() {
        balanceTemplateService = new BalanceTemplateService(balanceService);
        model = mock(Model.class);
    }

    @Test
    public void addIncomeParticularAttributeTest() {
        List<ParticularActivityDTO> particularIncomes = new ArrayList<>();
        particularIncomes.add(new ParticularActivityDTO(new BigDecimal("100"), LocalDate.of(2025, 3, 19), "salary"));
        particularIncomes.add(new ParticularActivityDTO(new BigDecimal("200"), LocalDate.of(2025, 3, 20), "allegro"));

        balanceTemplateService.addIncomeParticularAttribute(model, particularIncomes);

        verify(model).addAttribute("particularIncomes", particularIncomes);
    }

    @Test
    public void addExpenseParticularAttributeTest() {
        List<ParticularActivityDTO> particularExpenses = new ArrayList<>();
        particularExpenses.add(new ParticularActivityDTO(new BigDecimal("50"), LocalDate.of(2025, 3, 10), "transport"));
        particularExpenses.add(new ParticularActivityDTO(new BigDecimal("100"), LocalDate.of(2025, 3, 20), "food"));

        balanceTemplateService.addExpenseParticularAttribute(model, particularExpenses);

        verify(model).addAttribute("particularExpenses", particularExpenses);
    }

    @Test
    public void addIncomeSumAttributeTest() {
        List<ParticularActivityDTO> particularIncomes = new ArrayList<>();
        particularIncomes.add(new ParticularActivityDTO(new BigDecimal("100"), LocalDate.of(2025, 3, 19), "salary"));
        particularIncomes.add(new ParticularActivityDTO(new BigDecimal("200"), LocalDate.of(2025, 3, 20), "allegro"));

        BigDecimal incomesSum = balanceService.calculateTotalSum(particularIncomes);
        balanceTemplateService.addIncomeSumAttribute(model, particularIncomes);

        verify(model).addAttribute("incomesSum", incomesSum);
    }

    @Test
    public void addExpenseSumAttributeTest() {
        List<ParticularActivityDTO> particularExpenses = new ArrayList<>();
        particularExpenses.add(new ParticularActivityDTO(new BigDecimal("100"), LocalDate.of(2025, 3, 19), "transport"));
        particularExpenses.add(new ParticularActivityDTO(new BigDecimal("200"), LocalDate.of(2025, 3, 20), "food"));

        BigDecimal expensesSum = balanceService.calculateTotalSum(particularExpenses);
        balanceTemplateService.addExpenseSumAttribute(model, particularExpenses);

        verify(model).addAttribute("expensesSum", expensesSum);
    }

    @Test
    public void addIncomeCategoriesSumAttributeTest() {
        List<ParticularActivityDTO> particularIncomes = new ArrayList<>();
        particularIncomes.add(new ParticularActivityDTO(new BigDecimal("100"), LocalDate.of(2025, 3, 19), "salary"));
        particularIncomes.add(new ParticularActivityDTO(new BigDecimal("200"), LocalDate.of(2025, 3, 20), "allegro"));

        List<List<Object>> incomeCategoriesSum = balanceService.getCategoriesSum(particularIncomes);
        balanceTemplateService.addIncomeCategoriesSumAttribute(model, particularIncomes);

        verify(model).addAttribute("incomeCategoriesSum", incomeCategoriesSum);
    }

    @Test
    public void addExpenseCategoriesSumAttributeTest() {
        List<ParticularActivityDTO> particularExpenses = new ArrayList<>();
        particularExpenses.add(new ParticularActivityDTO(new BigDecimal("100"), LocalDate.of(2025, 3, 19), "transport"));
        particularExpenses.add(new ParticularActivityDTO(new BigDecimal("200"), LocalDate.of(2025, 3, 20), "food"));

        List<List<Object>> expenseCategoriesSum = balanceService.getCategoriesSum(particularExpenses);
        balanceTemplateService.addExpenseCategoriesSumAttribute(model, particularExpenses);

        verify(model).addAttribute("expenseCategoriesSum", expenseCategoriesSum);
    }

    @Test
    public void addWrongDateInputAttributeTest() {
        balanceTemplateService.addWrongDateInputAttribute(model);

        verify(model).addAttribute("wrongDateInput", true);
    }

    @Test
    public void addNoDateRangeAttributeTest() {
        balanceTemplateService.addNoDateRangeAttribute(model);

        verify(model).addAttribute("isDateRange", false);
    }
}
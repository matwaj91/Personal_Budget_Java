package PersonalBudget.business.income.domain;

import PersonalBudget.business.income.domain.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeFacadeTest {

    private final Long userId = 1L;

    @InjectMocks
    private IncomeFacade incomeFacade;

    @Mock
    private IncomeService incomeService;

    @Test
    void addDefaultIncomeCategoriesForUserTest() {
        incomeFacade.addDefaultIncomeCategoriesForUser(userId);

        verify(incomeService, times(1)).addDefaultIncomeCategoriesToUserAccount(userId);
    }

    @Test
    void deleteIncomesForUserTest() {
        incomeFacade.deleteIncomesForUser(userId);

        verify(incomeService, times(1)).deleteUserIncomes(userId);
    }

    @Test
    void deleteIncomeCategoriesForUserTest() {
        incomeFacade.deleteIncomeCategoriesForUser(userId);

        verify(incomeService, times(1)).deleteUserIncomeCategories(userId);
    }
}
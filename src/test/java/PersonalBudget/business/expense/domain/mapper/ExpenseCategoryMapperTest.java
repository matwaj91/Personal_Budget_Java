package PersonalBudget.business.expense.domain.mapper;

import PersonalBudget.business.expense.domain.model.ExpenseCategoryEntity;
import PersonalBudget.business.expense.domain.model.ExpensePaymentMethodEntity;
import PersonalBudget.business.expense.dto.ExpenseNewCategoryDTO;
import PersonalBudget.business.expense.dto.ExpenseNewPaymentMethodDTO;
import PersonalBudget.business.expense.dto.ExpensePaymentMethodDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseCategoryMapperTest {

    private final Long userId = 1L;

    @InjectMocks
    private ExpenseCategoryMapper expenseCategoryMapper;

    @Test
    void mapExpenseCategoryDTOToExpenseCategoryEntityTest() {
        ExpenseNewCategoryDTO expenseNewCategoryDTO = new ExpenseNewCategoryDTO("football");

        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryMapper.mapExpenseCategoryDTOToExpenseCategoryEntity(expenseNewCategoryDTO, userId);

        assertNotNull(expenseCategoryEntity);
        assertEquals(expenseNewCategoryDTO.name(), expenseCategoryEntity.getName());
        assertEquals(userId, expenseCategoryEntity.getUserId());
    }

    @Test
    void mapExpensePaymentMethodDTOToExpensePaymentMethodEntityTest() {
        ExpenseNewPaymentMethodDTO expenseNewPaymentMethodDTO = new ExpenseNewPaymentMethodDTO("cash");

        ExpensePaymentMethodEntity expensePaymentMethodEntity = expenseCategoryMapper.mapExpensePaymentMethodDTOToExpensePaymentMethodEntity(expenseNewPaymentMethodDTO, userId);

        assertNotNull(expensePaymentMethodEntity);
        assertEquals(expenseNewPaymentMethodDTO.name(), expensePaymentMethodEntity.getName());
        assertEquals(userId, expensePaymentMethodEntity.getUserId());
    }
}
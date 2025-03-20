package PersonalBudget.business.expense.domain.mapper;

import PersonalBudget.business.expense.domain.model.ExpenseEntity;
import PersonalBudget.business.expense.dto.ExpenseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseMapperTest {

    @InjectMocks
    private ExpenseMapper expenseMapper;

    @Test
    void mapExpenseDTOToExpenseEntityTest() {
        ExpenseDTO expenseDTO = new ExpenseDTO(
                new BigDecimal(300),
                LocalDate.of(2018, Month.JANUARY, 10),
                10L,
                5L,
                " ");

        Long userId = 1L;

        ExpenseEntity expenseEntity = expenseMapper.mapExpenseDTOToExpenseEntity(expenseDTO, userId);

        assertNotNull(expenseEntity);
        assertEquals(expenseDTO.amount(), expenseEntity.getAmount());
        assertEquals(expenseDTO.expenseDate(), expenseEntity.getExpenseDate());
        assertEquals(expenseDTO.expenseCategoryId(), expenseEntity.getExpenseCategoryId());
        assertEquals(expenseDTO.expenseComment(), expenseEntity.getExpenseComment());
        assertEquals(expenseDTO.paymentMethodId(), expenseEntity.getExpensePaymentMethodId());
    }
}
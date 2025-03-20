package PersonalBudget.business.income.domain.mapper;

import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.dto.IncomeDTO;
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
class IncomeMapperTest {

    @InjectMocks
    private IncomeMapper incomeMapper;

    @Test
    void mapIncomeDTOToIncomeEntityTest() {
        IncomeDTO incomeDTO = new IncomeDTO(
                new BigDecimal(1000),
                LocalDate.of(2018, Month.JANUARY, 5),
                4L,
                "");

        Long userId = 1L;

        IncomeEntity incomeEntity = incomeMapper.mapIncomeDTOToIncomeEntity(incomeDTO, userId);

        assertNotNull(incomeEntity);
        assertEquals(incomeDTO.amount(), incomeEntity.getAmount());
        assertEquals(incomeDTO.incomeDate(), incomeEntity.getIncomeDate());
        assertEquals(incomeDTO.incomeCategoryId(), incomeEntity.getIncomeCategoryId());
        assertEquals(incomeDTO.incomeComment(), incomeEntity.getIncomeComment());
    }
}
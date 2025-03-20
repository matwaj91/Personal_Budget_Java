package PersonalBudget.business.income.domain.mapper;

import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeCategoryMapperTest {

    @InjectMocks
    private IncomeCategoryMapper incomeCategoryMapper;

    @Test
    void mapIncomeCategoryDTOToIncomeCategoryEntityTest() {
        IncomeNewCategoryDTO incomeNewCategoryDTO = new IncomeNewCategoryDTO("casino");
        Long userId = 1L;

        IncomeCategoryEntity incomeCategoryEntity = incomeCategoryMapper.mapIncomeCategoryDTOToIncomeCategoryEntity(incomeNewCategoryDTO, userId);

        assertNotNull(incomeCategoryEntity);
        assertEquals(incomeNewCategoryDTO.name(), incomeCategoryEntity.getName());
        assertEquals(userId, incomeCategoryEntity.getUserId());
    }
}
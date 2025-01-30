package PersonalBudget.business.income.domain.mapper;

import PersonalBudget.business.income.domain.model.IncomeCategoryEntity;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class IncomeCategoryMapper {

    public IncomeCategoryEntity mapIncomeCategoryDTOToIncomeCategoryEntity(IncomeNewCategoryDTO incomeNewCategoryDTO, Long userId) {
        return IncomeCategoryEntity.builder()
                .userId(userId)
                .name(incomeNewCategoryDTO.name())
                .build();
    }
}

package PersonalBudget.business.income.domain.mapper;

import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.dto.IncomeDTO;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    public IncomeEntity mapIncomeDTOToIncomeEntity(IncomeDTO incomeDTO, Long userId) {
        return IncomeEntity.builder()
                .userId(userId)
                .incomeCategoryId(incomeDTO.incomeCategoryId())
                .amount(incomeDTO.amount())
                .incomeDate(incomeDTO.incomeDate())
                .incomeComment(incomeDTO.incomeComment())
                .build();
    }
}

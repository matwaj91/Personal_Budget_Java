package PersonalBudget.business.income.domain.mapper;


import PersonalBudget.business.income.domain.model.IncomeEntity;
import PersonalBudget.business.income.dto.IncomeDTO;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    public IncomeEntity mapIncomeDTOToIncomeEntity(IncomeDTO incomeDTO, Long userId, Long userIncomeCategory) {
        return IncomeEntity.builder()
                .user_id(userId)
                .income_category_assigned_to_user_id(userIncomeCategory)
                .amount(incomeDTO.amount())
                .date_of_income(incomeDTO.incomeDate())
                .income_comment(incomeDTO.incomeComment())
                .build();
    }
}

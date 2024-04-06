package PersonalBudget.business.balance.domain.mapper;

import PersonalBudget.common.util.CategorySumDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BalanceMapper {

    public List<Object> mapSumDTONameAndAmountToList(CategorySumDTO categorySumDTO) {
        return List.of(
                categorySumDTO.name(), categorySumDTO.totalAmount()
        );
    }

    public List<List<Object>> mapToChartData(List<CategorySumDTO> categoriesSum) {
        return categoriesSum.stream()
                .map(this::mapSumDTONameAndAmountToList)
                .toList();
    }
}

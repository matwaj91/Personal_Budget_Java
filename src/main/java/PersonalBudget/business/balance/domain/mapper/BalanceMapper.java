package PersonalBudget.business.balance.domain.mapper;

import PersonalBudget.common.util.ParticularActivityDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BalanceMapper {

    public List<List<Object>> mapParticularActivityDTOToNameAndTotalAmountList(List<ParticularActivityDTO> particularActivities) {
        return particularActivities.stream()
                .collect(Collectors.groupingBy(ParticularActivityDTO::name,
                        Collectors.reducing(BigDecimal.ZERO, ParticularActivityDTO::amount, BigDecimal::add)))
                .entrySet()
                .stream()
                .map(map -> List.of((Object)map.getKey(), map.getValue()))
                .toList();
    }
}

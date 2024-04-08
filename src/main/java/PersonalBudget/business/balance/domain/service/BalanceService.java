package PersonalBudget.business.balance.domain.service;

import PersonalBudget.business.balance.domain.BalanceGateway;
import PersonalBudget.business.balance.domain.mapper.BalanceMapper;
import PersonalBudget.common.util.ParticularActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BalanceService {

    private final BalanceGateway balanceGateway;
    private final BalanceMapper balanceMapper;

    public List<List<Object>> getCategoriesSum(List<ParticularActivityDTO> particularActivities) {
        return balanceMapper.mapParticularActivityDTOToNameAndTotalAmountList(particularActivities);
    }
    public List<ParticularActivityDTO> getUserParticularsIncomeCategory(LocalDate dateFrom, LocalDate dateTo) {
        return balanceGateway.fetchUserParticularsIncomeCategory(dateFrom, dateTo);
    }

    public List<ParticularActivityDTO> getUserParticularsExpensesCategory(LocalDate dateFrom, LocalDate dateTo) {
        return balanceGateway.fetchUserParticularsExpenseCategory(dateFrom, dateTo);
    }

    public BigDecimal calculateTotalSum(List<ParticularActivityDTO> particularActivities) {
        return particularActivities.stream()
                .map(ParticularActivityDTO::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

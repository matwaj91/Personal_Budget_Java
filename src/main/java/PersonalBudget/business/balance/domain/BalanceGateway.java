package PersonalBudget.business.balance.domain;

import PersonalBudget.business.expense.domain.service.ExpenseService;
import PersonalBudget.business.income.domain.service.IncomeService;
import PersonalBudget.common.util.ParticularActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BalanceGateway {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public List<ParticularActivityDTO> fetchUserParticularsIncomeCategory(LocalDate dateFrom, LocalDate dateTo) {
        return incomeService.getUserParticularsIncomeCategory(dateFrom, dateTo);
    }

    public List<ParticularActivityDTO> fetchUserParticularsExpenseCategory(LocalDate dateFrom, LocalDate dateTo) {
        return expenseService.getUserParticularsExpenseCategory(dateFrom, dateTo);
    }

}

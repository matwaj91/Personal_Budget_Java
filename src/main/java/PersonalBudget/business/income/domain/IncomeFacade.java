package PersonalBudget.business.income.domain;

import PersonalBudget.business.income.domain.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncomeFacade {

    private final IncomeService incomeService;

    public void addDefaultCategoriesForUser(Long id) {
        incomeService.addDefaultIncomeCategoriesToUserAccount(id);
    }
}

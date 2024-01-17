package PersonalBudget.business.income.domain;

import PersonalBudget.business.income.domain.service.IncomeService;
import PersonalBudget.business.user.domain.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncomeFacade {

    private final IncomeService incomeService;
    private final UserFacade userFacade;

    public void addDefaultCategoriesForUser(String email) {
        Long id = userFacade.fetchNewUserId(email);
        incomeService.addDefaultCategoriesToUserAccount(id);
    }
}

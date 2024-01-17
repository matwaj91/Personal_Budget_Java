package PersonalBudget.business.income.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeTemplateService {

    private final IncomeService incomeService;

    public void addIncomeCategoriesAttribute(Model model) {
        List<String> incomeCategories = incomeService.getCategoriesAssignedToUser();
        model.addAttribute("incomeCategories", incomeCategories);
    }

    public void addIncomeAttribute(Model model) {
        model.addAttribute("addedIncome", true);
    }
}

package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeTemplateService {

    private final IncomeService incomeService;

    public void addIncomeCategoriesAttribute(Model model) {
        List<IncomeCategoryDTO> incomeCategories = incomeService.getUserIncomeCategories();
        model.addAttribute("incomeCategories", incomeCategories);
    }

    public void addIncomeSuccessAttribute(Model model) {
        model.addAttribute("addedIncome", true);
    }
}

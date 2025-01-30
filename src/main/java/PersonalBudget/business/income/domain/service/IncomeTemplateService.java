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

    public void addIncomeCategoriesSuccessAttribute(Model model) {
        model.addAttribute("addedIncomeCategory", true);
    }

    public void addIncomeCategoriesFailureAttribute(Model model) {
        model.addAttribute("additionFailureIncomeCategory", true);
    }

    public void addIncomeCategoriesDeletionSuccessAttribute(Model model) {
        model.addAttribute("deletionIncomeCategory", true);
    }

    public void addIncomeCategoriesDeletionFailureAttribute(Model model) {
        model.addAttribute("deletionFailureIncomeCategory", true);
    }
}

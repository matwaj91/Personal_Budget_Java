package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class IncomeCategoryPageHandler {

    //private static final String NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "income-categories/success";
    //private static final String REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "redirect:" + INCOME_SUCCESS_PAGE;
    private final IncomeService incomeService;
    private static final String INCOME_CATEGORIES_PAGE = "menu/incomeCategories";

    public String handleIncomeCategoriesPage(Model model) {
        return INCOME_CATEGORIES_PAGE;
    }

    public String handleIncomeCategoriesPageAfterSubmit(BindingResult bindingResult, Model model, IncomeNewCategoryDTO incomeNewCategoryDTO) {
        if (bindingResult.hasErrors()) {
            return INCOME_CATEGORIES_PAGE;
        }
        incomeService.addIncomeCategory(incomeNewCategoryDTO);
        return INCOME_CATEGORIES_PAGE;
    }
}

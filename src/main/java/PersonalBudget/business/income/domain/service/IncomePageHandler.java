package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class IncomePageHandler {
    
    private final IncomeService incomeService;
    private final IncomeTemplateService incomeTemplateService;
    private static final String INCOME_PAGE = "menu/income";
    private static final String INCOME_SUCCESS_PAGE = "income/success";
    private static final String REDIRECT_INCOME_SUCCESS_PAGE  = "redirect:" + INCOME_SUCCESS_PAGE;

    public String handleIncomePage(Model model) {
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME_PAGE;
    }

    public String handleIncomePageAfterSubmit(BindingResult bindingResult, Model model, IncomeDTO incomeDTO) {
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        if (bindingResult.hasErrors()) {
            return INCOME_PAGE;
        }
        incomeService.addIncome(incomeDTO);
        return REDIRECT_INCOME_SUCCESS_PAGE;
    }

    public String handleIncomeSuccessPage(Model model) {
        incomeTemplateService.addIncomeSuccessAttribute(model);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME_PAGE;
    }
}

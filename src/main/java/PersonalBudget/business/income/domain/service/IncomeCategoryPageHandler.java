package PersonalBudget.business.income.domain.service;

import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeCategoryPageHandler {

    private final IncomeService incomeService;
    private final IncomeTemplateService incomeTemplateService;
    private static final String INCOME_CATEGORIES_PAGE = "menu/incomeCategories";
    private static final String NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "addition/success";
    private static final String REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE  = "redirect:" + NEW_INCOME_CATEGORY_SUCCESS_PAGE;
    private static final String INCOME_CATEGORY_DELETION_SUCCESS_PAGE  = "deletion/success";
    private static final String REDIRECT_INCOME_CATEGORY_DELETION_SUCCESS_PAGE = "redirect:" + INCOME_CATEGORY_DELETION_SUCCESS_PAGE;
    private static final String INCOME_CATEGORY_DELETION_FAILURE_PAGE  = "deletion/failure";
    private static final String REDIRECT_INCOME_CATEGORY_DELETION_FAILURE_PAGE  = "redirect:" + INCOME_CATEGORY_DELETION_FAILURE_PAGE;
    private static final String INCOME_CATEGORY_FAILURE_PAGE  = "addition/failure";
    private static final String REDIRECT_INCOME_CATEGORY_FAILURE_PAGE  = "redirect:" + INCOME_CATEGORY_FAILURE_PAGE;


    public String handleIncomeCategoriesPage(Model model) {
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME_CATEGORIES_PAGE;
    }

    public String handleIncomeCategoriesPageAfterSubmit(BindingResult bindingResult, Model model, IncomeNewCategoryDTO incomeNewCategoryDTO) {
        if (bindingResult.hasErrors()) {
            return INCOME_CATEGORIES_PAGE;
        }
        if(incomeService.checkIfCategoryNameAlreadyExists(incomeNewCategoryDTO)) {
            return REDIRECT_INCOME_CATEGORY_FAILURE_PAGE;
        }
        incomeService.addIncomeCategory(incomeNewCategoryDTO);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return REDIRECT_NEW_INCOME_CATEGORY_SUCCESS_PAGE;
    }

    public String handleIncomeCategoriesSuccessPage(Model model) {
        incomeTemplateService.addIncomeCategoriesSuccessAttribute(model);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME_CATEGORIES_PAGE;
    }

    public String handleIncomeDeletionCategoriesPageAfterSubmit(BindingResult bindingResult, Model model, IncomeCategoryDTO incomeCategoryDTO) {
        if (bindingResult.hasErrors()) {
            return INCOME_CATEGORIES_PAGE;
        }
        if(incomeService.checkIfIncomeCategoriesStored(incomeCategoryDTO)) {
            return REDIRECT_INCOME_CATEGORY_DELETION_FAILURE_PAGE;
        }
        incomeService.deleteIncomeCategory(incomeCategoryDTO);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return REDIRECT_INCOME_CATEGORY_DELETION_SUCCESS_PAGE;
    }

    public String handleIncomeCategoriesDeletionSuccessPage(Model model) {
        incomeTemplateService.addIncomeCategoriesDeletionSuccessAttribute(model);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME_CATEGORIES_PAGE;
    }

    public String handleIncomeCategoriesDeletionFailurePage(Model model) {
        incomeTemplateService.addIncomeCategoriesDeletionFailureAttribute(model);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME_CATEGORIES_PAGE;
    }

    public String handleIncomeCategoriesFailurePage(Model model) {
        incomeTemplateService.addIncomeCategoriesFailureAttribute(model);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME_CATEGORIES_PAGE;
    }
}

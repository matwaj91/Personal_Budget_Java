package PersonalBudget.business.income.domain.controller;

import PersonalBudget.business.income.domain.service.IncomeCategoryPageHandler;
import PersonalBudget.business.income.domain.service.IncomePageHandler;
import PersonalBudget.business.income.dto.IncomeCategoryDTO;
import PersonalBudget.business.income.dto.IncomeDTO;
import PersonalBudget.business.income.dto.IncomeNewCategoryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/income")
public class IncomeController {

    private final IncomePageHandler incomePageHandler;
    private final IncomeCategoryPageHandler incomeCategoryPageHandler;

    @GetMapping()
    public String getIncomePage(Model model) {
        return incomePageHandler.handleIncomePage(model);
    }

    @ModelAttribute("incomeDTO")
    public IncomeDTO incomeDTO(BigDecimal amount, LocalDate incomeDate, Long incomeCategoryId, String incomeComment) {
        return new IncomeDTO(amount, incomeDate, incomeCategoryId, incomeComment);
    }

    @PostMapping()
    public String getProperPageAfterAddingIncome(@Valid @ModelAttribute("incomeDTO") IncomeDTO incomeDTO,
                                                 BindingResult bindingResult, Model model) {
        return incomePageHandler.handleIncomePageAfterSubmit(bindingResult, model, incomeDTO);
    }

    @GetMapping(value = "/success")
    public String getIncomeSuccessPage(Model model) {
        return incomePageHandler.handleIncomeSuccessPage(model);
    }

    @GetMapping(value = "/income-categories")
    public String getIncomeCategoriesPage(Model model) {
       return  incomeCategoryPageHandler.handleIncomeCategoriesPage(model);
    }

    @ModelAttribute("incomeNewCategoryDTO")
    public IncomeNewCategoryDTO incomeNewCategoryDTO(String name) {
        return new IncomeNewCategoryDTO(name);
    }

    @PostMapping(value = "/income-categories/addition")
    public String getProperPageAfterAddingNewIncomeCategory(@Valid @ModelAttribute("incomeNewCategoryDTO") IncomeNewCategoryDTO incomeNewCategoryDTO,
                                                            BindingResult bindingResult, Model model) {
        return incomeCategoryPageHandler.handleIncomeCategoriesPageAfterSubmit(bindingResult, model, incomeNewCategoryDTO);
    }

    @GetMapping(value = "/income-categories/addition/success")
    public String getIncomeCategorySuccessPage(Model model) {
        return incomeCategoryPageHandler.handleIncomeCategoriesSuccessPage(model);
    }

    @GetMapping(value = "/income-categories/addition/failure")
    public String getIncomeCategoryFailurePage(Model model) {
        return incomeCategoryPageHandler.handleIncomeCategoriesFailurePage(model);
    }


    @ModelAttribute("incomeCategoryDTO")
    public IncomeCategoryDTO incomeNewCategoryDTO(Long id, String incomeCategory) {
        return new IncomeCategoryDTO(id, incomeCategory);
    }

    @PostMapping(value = "/income-categories/deletion")
    public String getProperPageAfterDeletingIncomeCategory(@Valid @ModelAttribute("incomeCategoryDTO") IncomeCategoryDTO incomeCategoryDTO,
                                                            BindingResult bindingResult, Model model) {
        return incomeCategoryPageHandler.handleIncomeDeletionCategoriesPageAfterSubmit(bindingResult, model, incomeCategoryDTO);
    }

    @GetMapping(value = "/income-categories/deletion/success")
    public String getIncomeCategoryDeletionSuccessPage(Model model) {
        return incomeCategoryPageHandler.handleIncomeCategoriesDeletionSuccessPage(model);
    }

    @GetMapping(value = "/income-categories/deletion/failure")
    public String getIncomeCategoryDeletionFailurePage(Model model) {
        return incomeCategoryPageHandler.handleIncomeCategoriesDeletionFailurePage(model);
    }
}

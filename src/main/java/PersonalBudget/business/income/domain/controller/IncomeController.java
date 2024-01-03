package PersonalBudget.business.income.domain.controller;

import PersonalBudget.business.income.domain.service.IncomeService;
import PersonalBudget.business.income.domain.service.IncomeTemplateService;
import PersonalBudget.business.income.dto.IncomeDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeTemplateService incomeTemplateService;
    private static final String INCOME = "menu/income";
    private static final String REDIRECT  = "redirect:income/success";


    @GetMapping()
    public String getIncomePage(Model model) {
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME;
    }

    @ModelAttribute("incomeDTO")
    public IncomeDTO incomeDTO(String amount, String incomeDate, String category, String incomeComment) {
        return new IncomeDTO(amount, incomeDate, category, incomeComment);
    }

    @PostMapping()
    public String getProperPageAfterAddingIncome(@Valid @ModelAttribute("incomeDTO") IncomeDTO incomeDTO,
                                           BindingResult bindingResult, Model model) {
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        if (bindingResult.hasErrors()) {
            return INCOME;
        }
        incomeService.addIncome(incomeDTO);
        return REDIRECT;
    }

    @GetMapping(value = "/success")
    public String getIncomeSuccessPage(Model model) {
        incomeTemplateService.addIncomeAttribute(model);
        incomeTemplateService.addIncomeCategoriesAttribute(model);
        return INCOME;
    }
}

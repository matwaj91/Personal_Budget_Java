package PersonalBudget.business.income.domain.controller;

import PersonalBudget.business.income.domain.service.IncomeService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/addIncome")
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping()
    public String getAddIncomePage(Model model) {
        List<String> incomeCategories = incomeService.getCategoriesAssignedToUser();
        model.addAttribute("incomeCategories", incomeCategories);
        return "menu/addIncome";
    }

    @ModelAttribute("incomeDTO")
    public IncomeDTO incomeDTO(String amount, String incomeDate, String category, String incomeComment) {
        return new IncomeDTO(amount, incomeDate, category, incomeComment);
    }

    @PostMapping()
    public String getProperPageAfterAddingIncome(@Valid @ModelAttribute("incomeDTO") IncomeDTO incomeDTO,
                                           BindingResult bindingResult, Model model) {
        List<String> incomeCategories = incomeService.getCategoriesAssignedToUser();
        model.addAttribute("incomeCategories", incomeCategories);
        if (bindingResult.hasErrors()) {
            return "menu/addIncome";
        }
        incomeService.addIncome(incomeDTO);
        return "redirect:addIncome/success";
    }

    @GetMapping(value = "/success")
    public String getIncomeSuccessPage(Model model) {
        model.addAttribute("addedIncome","addedIncome");
        List<String> incomeCategories = incomeService.getCategoriesAssignedToUser();
        model.addAttribute("incomeCategories", incomeCategories);
        return "menu/addIncome";
    }
}

package PersonalBudget.business.income.domain.controller;

import PersonalBudget.business.income.domain.service.IncomePageHandler;
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

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/income")
public class IncomeController {

    private final IncomePageHandler incomePageHandler;

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
        return incomePageHandler.handleExpenseSuccessPage(model);
    }
}

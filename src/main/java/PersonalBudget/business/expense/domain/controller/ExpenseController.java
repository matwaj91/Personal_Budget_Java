package PersonalBudget.business.expense.domain.controller;

import PersonalBudget.business.expense.domain.service.ExpensePageHandler;
import PersonalBudget.business.expense.dto.ExpenseDTO;
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
@RequestMapping("/api/v1/menu/expense")
public class ExpenseController {

    private final ExpensePageHandler expensePageHandler;

    @GetMapping()
    public String getExpensePage(Model model) {
        return expensePageHandler.handleExpensePage(model);
    }

    @ModelAttribute("expenseDTO")
    public ExpenseDTO expenseDTO(BigDecimal amount, LocalDate expenseDate, String paymentMethod, String category, String expenseComment) {
        return new ExpenseDTO(amount, expenseDate, paymentMethod, category, expenseComment);
    }

    @PostMapping()
    public String getExpensePageAfterSubmit(@Valid @ModelAttribute("expenseDTO") ExpenseDTO expenseDTO,
                                                 BindingResult bindingResult, Model model) {
        return expensePageHandler.handleExpensePageAfterSubmit(bindingResult, model, expenseDTO);
    }

    @GetMapping(value = "/success")
    public String getExpenseSuccessPage(Model model) {
        return expensePageHandler.handleExpenseSuccessPage(model);
    }
}

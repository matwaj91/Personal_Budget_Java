package PersonalBudget.business.balance.domain.controller;

import PersonalBudget.business.balance.domain.service.BalancePageHandler;
import PersonalBudget.common.util.NonstandardDateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class BalanceController {

    private final BalancePageHandler balancePageHandler;

    @GetMapping(value = "/current-month")
    public String getCurrentMonthBalancePage(Model model) {
        return balancePageHandler.handleCurrentMonthBalancePage(model);
    }

    @GetMapping(value = "/previous-month")
    public String getPreviousMonthBalancePage(Model model) {
        return balancePageHandler.handlePreviousMonthBalancePage(model);
    }

    @GetMapping(value = "/current-year")
    public String getCurrentYearBalancePage(Model model) {
        return balancePageHandler.handleCurrentYearBalancePage(model);
    }

    @ModelAttribute("nonstandardDateDTO")
    public NonstandardDateDTO nonstandardDateDTO(LocalDate dateFrom, LocalDate dateTo) {
        return new NonstandardDateDTO(dateFrom, dateTo);
    }

    @PostMapping(value = "/nonstandard")
    public String getNonstandardBalancePage(@Valid @ModelAttribute("nonstandardDateDTO") NonstandardDateDTO nonstandardDateDTO, Model model) {
        return balancePageHandler.handleNonstandardBalancePage(nonstandardDateDTO, model);
    }

    @GetMapping(value = "/nonstandard")
    public String getNonstandardBalancePage(Model model) {
        return balancePageHandler.handleNonstandardBalancePageWithoutDateRange(model);
    }


}

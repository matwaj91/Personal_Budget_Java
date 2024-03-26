package PersonalBudget.business.balance.domain.controller;

import PersonalBudget.business.balance.domain.service.BalancePageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class BalanceController {

    private final BalancePageHandler balancePageHandler;

    @GetMapping(value = "/current-month")
    public String getBalancePage(Model model) {
        return balancePageHandler.handleCurrentMonthBalancePage(model);
    }
}

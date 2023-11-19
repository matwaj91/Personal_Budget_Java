package PersonalBudget.business.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping(value = "/api/v1/menu")
    public String showMenuPage() {
        return "menu/main";
    }
}

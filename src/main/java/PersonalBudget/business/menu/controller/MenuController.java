package PersonalBudget.business.menu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class MenuController {

    @GetMapping(value = "/menu")
    public String getMenuPage() {
        return "menu/main";
    }
}

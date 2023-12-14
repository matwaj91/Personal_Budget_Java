package PersonalBudget.business.user.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = {"/", "/api/v1/login"})
    public String getLoginPage() {
        return "index";
    }
}
















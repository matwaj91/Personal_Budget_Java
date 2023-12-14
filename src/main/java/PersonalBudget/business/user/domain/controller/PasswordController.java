package PersonalBudget.business.user.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class PasswordController {

    @GetMapping(value = {"/password/forgot"})
    public String getResetPasswordPage() {
        return "password/forgot";
    }
}

package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping(value = {"/", "/api/v1/login"})
    public String showLoginPage() {
        return "index";
    }

    @PostMapping(value = "/api/v1/login")
    public String logInUser(){
        return "index";
   }
}
















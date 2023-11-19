package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.dto.LoginDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/api/v1/login"})
    public String showLoginPage() {
        return "index";
    }

    @ModelAttribute("loginDto")
    public LoginDto loginDto() {
        return new LoginDto();
    }

    @PostMapping(value = "/api/v1/login")
    public String logInUser(@Valid @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "index";
        }
        if(!userService.isEmail(loginDto)) {
            model.addAttribute("isRegistered", false);
            return "index";
        } else {
            if(userService.isLoginValid(loginDto)) {
                return "redirect:/api/v1/menu";
            } else {
                model.addAttribute("isPasswordValid", false);
                return "index";
            }
        }
   }

    @GetMapping(value = "/api/v1/logOut")
    public String logOut() {
        return "index";
    }
}
















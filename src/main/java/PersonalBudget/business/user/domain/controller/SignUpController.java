package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.dto.RegistrationDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/v1")
public class SignUpController {

    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/signup")
    public String showRegisterPage() {
        return "signup/signup";
    }

    @ModelAttribute("registrationDto")
    public RegistrationDto registrationDtoDto() {
        return new RegistrationDto();
    }

    @PostMapping(value = "/signup")
    public String registerNewUser(
            @Valid @ModelAttribute("registrationDto") @RequestBody RegistrationDto registrationDto,
            BindingResult bindingResult, Model model, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "signup/signup";
        }
        if(userService.isEmail(registrationDto)) {
            response.setStatus(HttpStatus.CONFLICT.value());
            model.addAttribute("isUserAlreadyRegistered", true);
            return "signup/signup";
        } else {
            userService.addNewUser(registrationDto);
            return "redirect:/api/v1/success";
        }
    }

    @GetMapping(value = "/success")
    public String showSuccessSignUpPage() {
        return "signup/success";
    }
}

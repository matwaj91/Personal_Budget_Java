package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SignUpController {

    private final UserService userService;

    @GetMapping(value = "/signup")
    public String getSignUpPage() {
        return "signup/signup";
    }

    @ModelAttribute("userDTO")
    public UserDTO userDTO(String name, String email, String password) {
        return new UserDTO(name, email, password);
    }

    @PostMapping(value = "/signup")
    public String getProperPageAfterSignUp(@Valid @ModelAttribute("userDTO") @RequestBody UserDTO userDTO,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "signup/signup";
        }
        if(userService.isEmail(userDTO)) {
            model.addAttribute("isUserAlreadyRegistered", true);
            return "signup/signup";
        }
        userService.addNewUser(userDTO);
        return "redirect:signup/success";
    }

    @GetMapping(value = "/signup/success")
    public String getSuccessSignUpPage() {
        return "signup/success";   
    }
}

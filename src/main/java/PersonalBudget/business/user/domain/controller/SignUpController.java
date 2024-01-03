package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
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
    private final UserTemplateService userTemplateService;
    public static final String SIGNUP = "signup/signup";
    public static final String SUCCESS = "signup/success";
    public static final String REDIRECT = "redirect:signup/success";


    @GetMapping(value = "/signup")
    public String getSignUpPage() {
        return SIGNUP;
    }

    @ModelAttribute("userDTO")
    public UserDTO userDTO(String name, String email, String password) {
        return new UserDTO(name, email, password);
    }

    @PostMapping(value = "/signup")
    public String getProperPageAfterSignUp(@Valid @ModelAttribute("userDTO") @RequestBody UserDTO userDTO,
                                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return SIGNUP;
        }
        if(userService.isEmail(userDTO)) {
            userTemplateService.addEmailVerificationAttribute(model);
            return SIGNUP;
        }
        userService.addNewUser(userDTO);
        return REDIRECT;
    }

    @GetMapping(value = "/signup/success")
    public String getSuccessSignUpPage() {
        return SUCCESS;
    }
}

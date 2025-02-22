package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.UserAccountPageHandler;
import PersonalBudget.business.user.domain.service.UserProfilePageHandler;
import PersonalBudget.business.user.dto.UserProfileDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/user-account")
public class UserAccountController {

    private final UserAccountPageHandler userAccountPageHandler;
    private final UserProfilePageHandler userProfilePageHandler;

    @GetMapping()
    public String getUserAccountPage(Model model) {
        return userAccountPageHandler.handleUserAccountPage(model);
    }

    @GetMapping(value = "/profile")
    public String getUserProfilePage(Model model) {
        return userProfilePageHandler.handleUserProfilePage(model);
    }

    @ModelAttribute("userProfileDTO")
    public UserProfileDTO userDTO(String name, String password) {
        return new UserProfileDTO(name, password);
    }

    @PostMapping(value = "/profile")
    public String getProperPageAfterSignUp(@Valid @ModelAttribute("userProfileDTO") @RequestBody UserProfileDTO userProfileDTO,
                                           BindingResult bindingResult, Model model) {
        return userProfilePageHandler.handleUserProfilePageAfterSubmit(bindingResult, model, userProfileDTO);
    }

    @GetMapping(value = "/profile/success")
    public String getUserProfileSuccessPage(Model model) {
        return userProfilePageHandler.handleUserProfileSuccessPage(model);
    }


}

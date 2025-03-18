package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.pageHandler.UserAccountPageHandler;
import PersonalBudget.business.user.domain.service.pageHandler.UserProfilePageHandler;
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
    public String getUserAccountPage() {
        return userAccountPageHandler.handleUserAccountPage();
    }

    @GetMapping(value = "/profile")
    public String getUserProfilePage() {
        return userProfilePageHandler.handleUserProfilePage();
    }

    @ModelAttribute("userProfileDTO")
    public UserProfileDTO userDTO(String name, String password) {
        return new UserProfileDTO(name, password);
    }

    @PostMapping(value = "/profile")
    public String getProperPageAfterProfilePageSubmit(@Valid @ModelAttribute("userProfileDTO") @RequestBody UserProfileDTO userProfileDTO,
                                           BindingResult bindingResult, Model model) {
        return userProfilePageHandler.handleUserProfilePageAfterSubmit(bindingResult, model, userProfileDTO);
    }

    @GetMapping(value = "/profile/success")
    public String getUserProfileSuccessPage(Model model) {
        return userProfilePageHandler.handleUserProfileSuccessPage(model);
    }

    @PostMapping(value = "/transactions")
    public String getProperPageAfterSignUp() {
        return userAccountPageHandler.handleUserTransactionsPageAfterSubmit();
    }

    @GetMapping(value = "/transactions/success")
    public String getUserTransactionsSuccessPage(Model model) {
        return userAccountPageHandler.handleUserTransactionsSuccessPage(model);
    }

    @PostMapping(value = "/deletion")
    public String getProperPageAfterAccountDeletion() {
        return userAccountPageHandler.handleUserAccountDeletionPageAfterSubmit();
    }

    @GetMapping(value = "/deletion/success")
    public String getUserAccountDeletionSuccessPage(Model model) {
        return userAccountPageHandler.handleUserAccountDeletionSuccessPage(model);
    }
}

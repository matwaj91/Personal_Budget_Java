package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import PersonalBudget.business.user.dto.UserProfileDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class UserProfilePageHandler {

    private final UserService userService;
    private final UserTemplateService userTemplateService;
    private static final String USER_PROFILE_PAGE = "menu/userProfile";
    private static final String USER_PROFILE_SUCCESS_PAGE = "profile/success";
    private static final String REDIRECT_USER_PROFILE_SUCCESS_PAGE  = "redirect:" + USER_PROFILE_SUCCESS_PAGE;

    public String handleUserProfilePage() {
        return USER_PROFILE_PAGE;
    }

    public String handleUserProfilePageAfterSubmit(BindingResult bindingResult, Model model,
                                                   @Valid UserProfileDTO userProfileDTO) {
        if (bindingResult.hasErrors()) {
            return USER_PROFILE_PAGE;
        }
        if (userProfileDTO.name().isEmpty()) {
            userService.updateUserPassword(userProfileDTO);
        } else if (userProfileDTO.password().isEmpty()) {
            userService.updateUserName(userProfileDTO);
        } else {
            userService.updateUserNameAndPassword(userProfileDTO);
        }
        return REDIRECT_USER_PROFILE_SUCCESS_PAGE;
    }

    public String handleUserProfileSuccessPage(Model model) {
        userTemplateService.addProfileDetailsAttribute(model);
        return handleUserProfilePage();
    }
}

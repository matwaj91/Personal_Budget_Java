package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import PersonalBudget.business.user.dto.UserPasswordDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResetPasswordPageHandler {

    private final UserService userService;
    private final UserTemplateService userTemplateService;
    private static final String PASSWORD_RESET_PAGE = "password/reset";
    private static final String LOGIN_PAGE = "index";
    private static final String PASSWORD_RESET_SUCCESS_PAGE = "password/resetSuccess";

    public String handleResetPasswordPage(String token, HttpSession session) {
        Optional<String> optionalEmail = userService.isAccount(token);
        if(optionalEmail.isEmpty()){
            return LOGIN_PAGE;
        }
        session.setAttribute("email", optionalEmail.get());
        return PASSWORD_RESET_PAGE;
    }

    public String handleResetPasswordPageAfterSubmit(BindingResult bindingResult, Model model,
                                                     @Valid UserPasswordDTO userPasswordDTO, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return PASSWORD_RESET_PAGE;
        }
        Optional<String> optionalEmail = Optional.ofNullable((String) session.getAttribute("email"));
        if(optionalEmail.isPresent()) {
            userService.resetPassword(optionalEmail.get(), userPasswordDTO.password());
            return PASSWORD_RESET_SUCCESS_PAGE;
        } else {
            userTemplateService.addNoResetPasswordAttribute(model);
            return PASSWORD_RESET_PAGE;
        }
    }
}

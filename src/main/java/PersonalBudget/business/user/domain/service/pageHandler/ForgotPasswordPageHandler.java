package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.user.domain.service.EmailService;
import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import PersonalBudget.business.user.dto.UserEmailDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static PersonalBudget.common.util.TokenGenerator.getGeneratedToken;

@RequiredArgsConstructor
@Service
public class ForgotPasswordPageHandler {

    private static final String FORGOT_PASSWORD_PAGE = "password/forgot";
    private static final String RESET_REQUESTED_PAGE = "password/resetRequested";
    private final UserService userService;
    private final EmailService emailService;
    private final UserTemplateService userTemplateService;

    public String handleResetPasswordPage() {
        return FORGOT_PASSWORD_PAGE;
    }

    public String handleForgotPasswordPageAfterSubmit(BindingResult bindingResult, Model model,
                                                      @Valid UserEmailDTO userEmailDTO) {
        if (bindingResult.hasErrors()) {
            return FORGOT_PASSWORD_PAGE;
        }
        if(!userService.isEmail(userEmailDTO.email())) {
            userTemplateService.addMissingEmailAttribute(model);
            return FORGOT_PASSWORD_PAGE;
        }
        String token = getGeneratedToken();
        userService.updatePasswordResetToken(userEmailDTO, token);
        String emailMessage = emailService.buildResetPasswordEmail(token);
        String subject = emailService.buildResetPasswordSubject();
        emailService.sendEmail(emailMessage, userEmailDTO.email() , subject);
        return RESET_REQUESTED_PAGE;
    }
}

package PersonalBudget.business.user.domain.controller;

import PersonalBudget.business.user.domain.service.pageHandler.ForgotPasswordPageHandler;
import PersonalBudget.business.user.domain.service.pageHandler.ResetPasswordPageHandler;
import PersonalBudget.business.user.dto.UserEmailDTO;
import PersonalBudget.business.user.dto.UserPasswordDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PasswordController {

    private final ForgotPasswordPageHandler forgotPasswordPageHandler;
    private final ResetPasswordPageHandler resetPasswordPageHandler;


    @GetMapping(value = "/password/forgot")
    public String getResetPasswordPage() {
        return forgotPasswordPageHandler.handleResetPasswordPage();
    }

    @ModelAttribute("userEmailDTO")
    public UserEmailDTO userEmailDTO(String email) {
        return new UserEmailDTO(email);
    }

    @PostMapping(value = "/password/forgot")
    public String getProperPageAfterForgotPassword(@Valid @ModelAttribute("userEmailDTO") @RequestBody UserEmailDTO userEmailDTO,
                                           BindingResult bindingResult, Model model) {
        return forgotPasswordPageHandler.handleForgotPasswordPageAfterSubmit(bindingResult, model, userEmailDTO);
    }

    @GetMapping(path = "/password/reset")
    public String getProperPageAfterResetLinkSubmit(@RequestParam("token") String token, HttpSession session) {
        return resetPasswordPageHandler.handleResetPasswordPage(token, session);
    }

    @ModelAttribute("userPasswordDTO")
    public UserPasswordDTO userPasswordDTO(String password) {
        return new UserPasswordDTO(password);
    }

    @PostMapping(value = "/password/reset")
    public String getProperPageAfterResetPasswordSubmit(@Valid @ModelAttribute("userPasswordDTO") @RequestBody UserPasswordDTO userPasswordDTO,
                                           BindingResult bindingResult, Model model, HttpSession session) {
        return resetPasswordPageHandler.handleResetPasswordPageAfterSubmit(bindingResult, model, userPasswordDTO,session);
    }
}

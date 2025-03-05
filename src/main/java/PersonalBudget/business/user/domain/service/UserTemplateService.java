package PersonalBudget.business.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserTemplateService {

    public void addEmailVerificationAttribute(Model model) {
        model.addAttribute("isUserAlreadyRegistered", true);
    }

    public void addProfileDetailsAttribute(Model model) {
        model.addAttribute("isProfileUpdated", true);
    }

    public void deleteTransactionsAttribute(Model model) {
        model.addAttribute("isTransactionDeleted", true);
    }

    public void deleteUserAccountAttribute(Model model) {
        model.addAttribute("isAccountDeleted", true);
    }

    public void addMissingEmailAttribute(Model model) {
        model.addAttribute("isUserAlreadyRegistered", false);
    }

    public void addNoResetPasswordAttribute(Model model) {
        model.addAttribute("isPasswordReset", false);
    }
}

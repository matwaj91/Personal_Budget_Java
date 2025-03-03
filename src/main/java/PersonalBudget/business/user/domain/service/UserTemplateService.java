package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.dto.UserProfileDTO;
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

    public void addConfirmationEmailAttribute(Model model, String name, String link) {
        model.addAttribute("name", name);
        model.addAttribute("link", link);
    }
}

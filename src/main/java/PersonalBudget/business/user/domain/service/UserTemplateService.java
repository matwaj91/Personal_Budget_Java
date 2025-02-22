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
}

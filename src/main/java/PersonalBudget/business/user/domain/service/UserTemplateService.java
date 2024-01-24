package PersonalBudget.business.user.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserTemplateService {

    public void addEmailVerificationAttribute(Model model) {
        model.addAttribute("isUserAlreadyRegistered", true);
    }
}

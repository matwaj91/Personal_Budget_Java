package PersonalBudget.business.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserAccountPageHandler {

    private final UserService userService;
    private final UserTemplateService userTemplateService;
    private static final String USER_ACCOUNT_PAGE = "menu/userAccount";

    public String handleUserAccountPage(Model model) {
        return USER_ACCOUNT_PAGE;
    }
}

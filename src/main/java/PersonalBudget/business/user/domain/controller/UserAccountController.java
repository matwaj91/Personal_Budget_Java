package PersonalBudget.business.user.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu/user-account")
public class UserAccountController {

    @GetMapping()
    public String getUserAccountPage() {
        return "menu/userAccount";
    }

    @GetMapping(value = "/profile")
    public String getUserProfilePage() {
        return "menu/userProfile";
    }


}

package PersonalBudget.business.user.domain;

import PersonalBudget.business.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public Long fetchLoggedInUserId() {
        return userService.getCurrentLoggedInUserId();
    }

    public Long fetchNewUserId(String email) {
        return userService.getNewUserId(email);
    }
}

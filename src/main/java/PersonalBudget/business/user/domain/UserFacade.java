package PersonalBudget.business.user.domain;

import PersonalBudget.business.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public Long fetchLoggedInUserId() {
        return userService.getCurrentLoggedInUserId();
    }
}

package PersonalBudget.business.user.domain.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User id not found")
public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException(String message) {
        super(message);
    }
}


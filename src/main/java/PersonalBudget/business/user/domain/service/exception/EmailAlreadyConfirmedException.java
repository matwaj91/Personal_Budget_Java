package PersonalBudget.business.user.domain.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email already confirmed")
public class EmailAlreadyConfirmedException extends RuntimeException {

    public EmailAlreadyConfirmedException(String message) {
        super(message);
    }
}

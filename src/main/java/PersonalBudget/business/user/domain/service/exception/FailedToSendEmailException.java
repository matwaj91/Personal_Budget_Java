package PersonalBudget.business.user.domain.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed to send an email")
public class FailedToSendEmailException extends RuntimeException {

    public FailedToSendEmailException(String message) {
        super(message);
    }
}

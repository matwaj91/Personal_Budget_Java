package PersonalBudget.business.income.domain.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Income category id not found")
public class IncomeCategoryIdNotFoundException extends RuntimeException {

    public IncomeCategoryIdNotFoundException(String message) {
        super(message);
    }
}

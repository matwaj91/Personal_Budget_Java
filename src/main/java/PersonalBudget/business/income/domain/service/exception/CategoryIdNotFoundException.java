package PersonalBudget.business.income.domain.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Category id not found")
public class CategoryIdNotFoundException extends RuntimeException {

    public CategoryIdNotFoundException(String message) {
        super(message);
    }
}

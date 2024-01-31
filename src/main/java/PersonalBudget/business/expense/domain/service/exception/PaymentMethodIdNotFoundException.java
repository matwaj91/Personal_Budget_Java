package PersonalBudget.business.expense.domain.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Payment method id not found")
public class PaymentMethodIdNotFoundException extends RuntimeException {

    public PaymentMethodIdNotFoundException(String message) {
        super(message);
    }
}

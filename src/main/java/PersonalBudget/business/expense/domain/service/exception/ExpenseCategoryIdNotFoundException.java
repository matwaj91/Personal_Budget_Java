package PersonalBudget.business.expense.domain.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Expense category id not found")
public class ExpenseCategoryIdNotFoundException extends RuntimeException {

    public ExpenseCategoryIdNotFoundException(String message) {
        super(message);
    }
}

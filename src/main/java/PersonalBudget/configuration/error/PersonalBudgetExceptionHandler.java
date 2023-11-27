package PersonalBudget.configuration.error;

import PersonalBudget.business.user.domain.service.exception.UserAlreadyExistsException;
import PersonalBudget.business.user.domain.service.exception.UserNotFoundException;
import PersonalBudget.common.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@ControllerAdvice
public class PersonalBudgetExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .uuid(UUID.randomUUID())
                .timestamp(LocalDateTime.now())
                .requestPath(request.getRequestURI())
                .message(exception.getMessage())
                .details(Arrays.toString(exception.getStackTrace()))
                .build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(UserAlreadyExistsException exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .uuid(UUID.randomUUID())
                .timestamp(LocalDateTime.now())
                .requestPath(request.getRequestURI())
                .message(exception.getMessage())
                .details(Arrays.toString(exception.getStackTrace()))
                .build();
    }
}

package PersonalBudget.configuration.error;

import PersonalBudget.business.user.domain.service.exception.UserNotFoundException;
import PersonalBudget.common.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerConfigTest {

    @Mock
    private HttpServletRequest request;

    @Test
    void handleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");
        ExceptionHandlerConfig exceptionHandlerConfig = new ExceptionHandlerConfig();

        ErrorResponse errorResponse = exceptionHandlerConfig.handleUserNotFoundException(exception, request);

        assertEquals("User not found", errorResponse.message());
        assertEquals(exception.getMessage(), errorResponse.message());
    }
}
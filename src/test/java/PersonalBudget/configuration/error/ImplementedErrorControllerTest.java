package PersonalBudget.configuration.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ImplementedErrorControllerTest {

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        HttpServletRequest request = mock(HttpServletRequest.class);
    }

    @Test
    void handle404ErrorTest() {
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(404);

        String response = new ImplementedErrorController().handleError(request);

        assertEquals("404", response);
    }

    @Test
    void handle500ErrorTest() {
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(500);

        String response = new ImplementedErrorController().handleError(request);

        assertEquals("500", response);
    }

    @Test
    void handleNullStatusTest() {
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(null);

        String response = new ImplementedErrorController().handleError(request);

        assertNull(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        assertEquals("error", response);
    }
}
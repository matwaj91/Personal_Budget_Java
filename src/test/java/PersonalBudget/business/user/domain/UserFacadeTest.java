package PersonalBudget.business.user.domain;

import PersonalBudget.business.user.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class UserFacadeTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacade userFacade;

    @Test
    void fetchLoggedInUserIdTestTest() {
        userFacade.fetchLoggedInUserId();

        verify(userService, times(1)).getCurrentLoggedInUserId();
    }
}
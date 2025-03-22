package PersonalBudget.business.user.domain.service.pageHandler;

import PersonalBudget.business.user.domain.service.UserService;
import PersonalBudget.business.user.domain.service.UserTemplateService;
import PersonalBudget.business.user.dto.UserProfileDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserProfilePageHandlerTest {

    private final UserProfileDTO userProfileDTO = new UserProfileDTO("Mateusz", "1253mk@s");
    private static final String USER_PROFILE_PAGE = "menu/userProfile";
    private static final String USER_PROFILE_SUCCESS_PAGE = "profile/success";
    private static final String REDIRECT_USER_PROFILE_SUCCESS_PAGE  = "redirect:" + USER_PROFILE_SUCCESS_PAGE;

    @Mock
    private Model model;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserTemplateService userTemplateService;

    @InjectMocks
    private UserProfilePageHandler userProfilePageHandler;

    @Test
    void handleUserProfilePageTest() {
        String result = userProfilePageHandler.handleUserProfilePage();

        assertEquals(USER_PROFILE_PAGE, result);
    }

    @Test
    void handleUserProfilePageHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = userProfilePageHandler.handleUserProfilePageAfterSubmit(bindingResult, model, userProfileDTO);

        assertEquals(USER_PROFILE_PAGE, result);
    }

    @Test
    void handleUserProfilePageNameEmpty() {
        UserProfileDTO userProfileNoName = new UserProfileDTO("", "1253mk@s");
        UserService userService = mock(UserService.class);

        userProfilePageHandler.handleUserProfilePageAfterSubmit(bindingResult, model, userProfileNoName);

        userService.updateUserPassword(userProfileNoName);

        verify(userService, times(1)).updateUserPassword(userProfileNoName);
    }

    @Test
    void handleUserProfilePagePasswordEmpty() {
        UserProfileDTO userProfileNoPassword = new UserProfileDTO("Mateusz", "");
        UserService userService = mock(UserService.class);

        userProfilePageHandler.handleUserProfilePageAfterSubmit(bindingResult, model, userProfileNoPassword);

        userService.updateUserName(userProfileNoPassword);

        verify(userService, times(1)).updateUserName(userProfileNoPassword);
    }

    @Test
    void handleUserProfilePageAfterSubmitTest() {
        String result = userProfilePageHandler.handleUserProfilePageAfterSubmit(bindingResult, model, userProfileDTO);

        assertEquals(REDIRECT_USER_PROFILE_SUCCESS_PAGE, result);
    }

    @Test
    void handleUserProfileSuccessPageTest() {
        String result = userProfilePageHandler.handleUserProfileSuccessPage(model);

        assertEquals(USER_PROFILE_PAGE, result);
    }
}
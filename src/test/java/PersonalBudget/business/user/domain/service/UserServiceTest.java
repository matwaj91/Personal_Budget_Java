package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.mapper.UserMapper;
import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.domain.service.exception.EmailAlreadyConfirmedException;
import PersonalBudget.business.user.domain.service.exception.TokenNotFoundException;
import PersonalBudget.business.user.domain.service.exception.UserNotFoundException;
import PersonalBudget.business.user.dto.UserDTO;
import PersonalBudget.business.user.dto.UserEmailDTO;
import PersonalBudget.business.user.dto.UserProfileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final String NAME ="MATEUSZ";
    private final String EMAIL = "test@example.com";
    private final String PASSWORD = "testPassword";
    private final String TOKEN = "123456789";
    private final String PASSWORD_RESET_TOKEN = "dasdk234";
    private final Long EXPECTED_USER_ID = 1L;
    private final UserProfileDTO userProfileDTO = new UserProfileDTO(NAME, PASSWORD);

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void addNewUserShouldAddNewUserSuccessfully() {
        UserDTO userDTO = new UserDTO(NAME, EMAIL, PASSWORD);
        UserAccountEntity userEntity = new UserAccountEntity();
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.password());

        when(userMapper.mapUserDTOToUserEntity(eq(userDTO), eq(encodedPassword), any(String.class)))
                .thenReturn(userEntity);

        when(userRepository.save(any(UserAccountEntity.class))).thenReturn(userEntity);

        UserAccountEntity addedUser = userService.addNewUser(userDTO);
        assertNotNull(addedUser);
        assertEquals(userEntity.getName(), addedUser.getName());
        assertEquals(EMAIL, userDTO.email());
        assertNotEquals(PASSWORD, userEntity.getPassword());
    }

    @Test
    public void enableUserAccountTest() {
        UserAccountEntity userAccountEntity = new UserAccountEntity();

        when(userRepository.findUserByToken(TOKEN)).thenReturn(Optional.of(userAccountEntity));

        userService.enableUserAccount(TOKEN);

        verify(userRepository, times(1)).enableUserAccount(userAccountEntity.getEmail());
    }

    @Test
    public void enableUserAccountFailureTest() {
        when(userRepository.findUserByToken(TOKEN)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> userService.enableUserAccount(TOKEN));
    }

    @Test
    public void enableUserAccountIsEnabledTest() {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setEnabled(true);

        when(userRepository.findUserByToken(TOKEN)).thenReturn(Optional.of(userAccountEntity));
        assertThrows(EmailAlreadyConfirmedException.class, () -> userService.enableUserAccount(TOKEN));
    }

    @Test
    void isEmailTest() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        boolean result = userService.isEmail(EMAIL);

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail(EMAIL);
    }

    @Test
    public void getCurrentLoggedInUserIdTest() {
        authenticateUser(EMAIL, EXPECTED_USER_ID);
        Long loggedUserId = userService.getCurrentLoggedInUserId();

        assertEquals(EXPECTED_USER_ID, loggedUserId);
    }

    @Test
    public void getCurrentLoggedInUserIdFailureTest() {
        authenticateUser(EMAIL, EXPECTED_USER_ID);

        when(userRepository.findIdByEmail(EMAIL)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getCurrentLoggedInUserId(),
                "UserNotFoundException should be thrown"
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void loadUserByUsernameTest() {
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(EMAIL),
                "UsernameNotFoundException should be thrown"
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void getUserProfileDetailsTest() {
        authenticateUser(EMAIL, EXPECTED_USER_ID);
        Long loggedInUserId = userService.getCurrentLoggedInUserId();

        userService.getUserProfileDetails();

        verify(userRepository, times(1)).findUserProfileDetailsByUserId(loggedInUserId);
    }

    @Test
    public void updateUserNameTest() {
        authenticateUser(EMAIL, EXPECTED_USER_ID);
        Long loggedInUserId = userService.getCurrentLoggedInUserId();

        userService.updateUserName(userProfileDTO);

        verify(userRepository, times(1)).setUserName(loggedInUserId, NAME);
    }

    @Test
    public void updateUserPasswordTest() {
        authenticateUser(EMAIL, EXPECTED_USER_ID);
        Long loggedInUserId = userService.getCurrentLoggedInUserId();
        String encodedPassword = bCryptPasswordEncoder.encode(userProfileDTO.password());

        userService.updateUserPassword(userProfileDTO);

        verify(userRepository, times(1)).setUserPassword(loggedInUserId, encodedPassword);
    }

    @Test
    public void updateUserNameAndPasswordTest() {
        authenticateUser(EMAIL, EXPECTED_USER_ID);
        Long loggedInUserId = userService.getCurrentLoggedInUserId();
        String encodedPassword = bCryptPasswordEncoder.encode(userProfileDTO.password());

        userService.updateUserNameAndPassword(userProfileDTO);

        verify(userRepository, times(1)).setUserNameAndPassword(loggedInUserId, NAME, encodedPassword);
    }

    @Test
    public void deleteUserAccountTest() {
        Long userId = 1L;

        userService.deleteUserAccount(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void updatePasswordResetTokenTest() {
        UserEmailDTO userEmailDTO = new UserEmailDTO(EMAIL);
        userService.updatePasswordResetToken(userEmailDTO, PASSWORD_RESET_TOKEN);

        verify(userRepository, times(1)).setPasswordResetToken(EMAIL, PASSWORD_RESET_TOKEN);
    }

    @Test
    public void isAccountTest() {
        userService.isAccount( TOKEN);

        verify(userRepository, times(1)).existsByToken(TOKEN);
    }

    @Test
    public void resetPasswordTest() {
        userService.resetPassword(EMAIL, PASSWORD);

        String encodedPassword = bCryptPasswordEncoder.encode(PASSWORD);

        verify(userRepository, times(1)).setUserPasswordAfterReset(EMAIL, encodedPassword);
    }

    public void authenticateUser(String email, Long expectedUserId) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(email);
        when(userRepository.findIdByEmail(email)).thenReturn(Optional.of(expectedUserId));

        SecurityContextHolder.setContext(securityContext);
    }
}
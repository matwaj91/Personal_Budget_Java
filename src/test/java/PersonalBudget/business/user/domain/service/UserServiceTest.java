package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.mapper.UserMapper;
import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.domain.service.exception.UserNotFoundException;
import PersonalBudget.business.user.dto.UserDTO;
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
        UserDTO userDTO = new UserDTO("testName", "testEmail", "testPassword");
        UserAccountEntity userEntity = new UserAccountEntity();
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.password());

        when(userMapper.mapUserDTOToUserEntity(eq(userDTO), eq(encodedPassword), any(String.class)))
                .thenReturn(userEntity);

        when(userRepository.save(any(UserAccountEntity.class))).thenReturn(userEntity);

        UserAccountEntity addedUser = userService.addNewUser(userDTO);
        assertNotNull(addedUser);
        assertEquals(userEntity.getName(), addedUser.getName());
        assertEquals("testEmail", userDTO.email());
        assertNotEquals("testpassword", userEntity.getPassword());
    }

    @Test
    void isEmailTest() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userService.isEmail(email);

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    public void getCurrentLoggedInUserIdTest() {
        String email = "test@example.com";
        Long expectedUserId = 1L;

        authenticateUser(email, expectedUserId);
        Long loggedUserId = userService.getCurrentLoggedInUserId();

        assertEquals(expectedUserId, loggedUserId);
    }

    @Test
    public void getCurrentLoggedInUserIdFailureTest() {
        String email = "test@example.com";
        Long expectedUserId = 1L;

        authenticateUser(email, expectedUserId);

        when(userRepository.findIdByEmail(email)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getCurrentLoggedInUserId(),
                "UserNotFoundException should be thrown"
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void loadUserByUsernameTest() {
        String email = "test@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(email),
                "UsernameNotFoundException should be thrown"
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void updatePasswordResetTokenTest() {
        String email = "test@example.com";
        String passwordResetToken = "14$csd839";

        userRepository.setUserPasswordAfterReset(email, passwordResetToken);

        verify(userRepository, times(1)).setUserPasswordAfterReset(email, passwordResetToken);

    }

    @Test
    public void isAccountSuccessTest() {
        String token = "123456789";
        when(userRepository.existsByToken(token)).thenReturn(Optional.of("test@example.com"));

        Optional<String> result = userService.isAccount(token);

        assertTrue(result.isPresent());
    }

    @Test
    public void resetPasswordTest() {
        String email = "test@example.com";
        String password = "testPassword";
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        userRepository.setUserPasswordAfterReset(email, encodedPassword);

        verify(userRepository, times(1)).setUserPasswordAfterReset(email, encodedPassword);
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
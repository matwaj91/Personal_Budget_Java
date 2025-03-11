package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.mapper.UserMapper;
import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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
}
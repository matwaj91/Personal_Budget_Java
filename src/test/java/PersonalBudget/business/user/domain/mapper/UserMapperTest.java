package PersonalBudget.business.user.domain.mapper;

import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserMapperTest {

    @Test
    public void mapUserDTOToUserEntityTest() {
        String encodedPassword = "encodedPassword";
        String generatedToken = "generatedToken";
        UserDTO userDTO = new UserDTO("Mateusz", "test@example.com", encodedPassword);

        UserAccountEntity userAccount = new UserMapper().mapUserDTOToUserEntity(userDTO, encodedPassword, generatedToken);

        assertNotNull(userAccount);
        assertEquals(userDTO.name(), userAccount.getName());
        assertEquals(userDTO.email(), userAccount.getEmail());
        assertEquals(generatedToken, userAccount.getAccountConfirmationToken());
    }
}
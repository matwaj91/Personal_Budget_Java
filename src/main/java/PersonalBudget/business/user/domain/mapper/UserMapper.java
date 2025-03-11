package PersonalBudget.business.user.domain.mapper;

import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserAccountEntity mapUserDTOToUserEntity(UserDTO userDTO, String encodedPassword, String generatedToken) {
        return UserAccountEntity.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(encodedPassword)
                .accountConfirmationToken(generatedToken)
                .build();
    }
}





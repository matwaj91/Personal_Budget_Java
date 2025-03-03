package PersonalBudget.business.user.domain.mapper;

import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public record UserMapper() {

    public UserAccountEntity mapUserDTOToUserEntity(UserDTO userDTO, BCryptPasswordEncoder bCryptPasswordEncoder, String token) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.password());
        return UserAccountEntity.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(encodedPassword)
                .token(token)
                .build();
    }
}





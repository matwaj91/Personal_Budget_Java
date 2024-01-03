package PersonalBudget.business.user.domain.mapper;

import PersonalBudget.business.user.domain.model.UserEntity;
import PersonalBudget.business.user.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public record UserMapper() {

    public UserEntity mapUserDTOToUserEntity(UserDTO userDTO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.password());

        return UserEntity.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(encodedPassword)
                .build();
    }
}





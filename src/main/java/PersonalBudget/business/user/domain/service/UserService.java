package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.model.UserEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.dto.RegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public boolean isEmail(RegistrationDTO registrationDto) {
        Optional<UserEntity> optionalUser = userRepository
                .findUserByEmail(registrationDto.email());
        if (optionalUser.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void addNewUser(RegistrationDTO registrationDTO) {
        String encodedPassword = bCryptPasswordEncoder.encode(registrationDTO.password());

        UserEntity userEntity = modelMapper.map(registrationDTO, UserEntity.class);
        /*userEntity = new UserEntity().builder()
                .name(registrationDTO.name())
                .email(registrationDTO.email())
                .password(encodedPassword)
                .build();*/

        userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }
}


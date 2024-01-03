package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.income.domain.repository.IncomeCategoryRepository;
import PersonalBudget.business.user.domain.mapper.UserMapper;
import PersonalBudget.business.user.domain.model.UserEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean isEmail(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            return true;
        } else {
            return false;
        }
    }

    public void addNewUser(UserDTO userDTO) {
        UserEntity userEntity = userMapper.mapUserDTOToUserEntity(userDTO, bCryptPasswordEncoder);
        userRepository.save(userEntity);
        Long userId = userRepository.findIdByEmail(userDTO.email());
        incomeCategoryRepository.addDefaultCategories(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }
}


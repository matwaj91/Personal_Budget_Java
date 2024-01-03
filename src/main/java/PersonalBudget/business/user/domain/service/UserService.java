package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.income.domain.service.IncomeService;
import PersonalBudget.business.user.domain.mapper.UserMapper;
import PersonalBudget.business.user.domain.model.UserEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.domain.service.exception.UserNotFoundException;
import PersonalBudget.business.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final IncomeService incomeService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean isEmail(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.email())) {
            return true;
        }
        return false;
    }

    public void addNewUser(UserDTO userDTO) {
        UserEntity userEntity = userMapper.mapUserDTOToUserEntity(userDTO, bCryptPasswordEncoder);
        userRepository.save(userEntity);
        Long userId = userEntity.getId();
        incomeService.addDefaultCategories(userId);
    }

    /**
     * retrieve the currently logged-in user details
     */
    public Long getCurrentLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findIdByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }
}


package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.mapper.UserMapper;
import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.domain.service.exception.UserIdNotFoundException;
import PersonalBudget.business.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean isEmail(UserDTO userDTO) {
        return userRepository.existsByEmail(userDTO.email());
    }

    public void addNewUser(UserDTO userDTO) {
        UserAccountEntity userEntity = userMapper.mapUserDTOToUserEntity(userDTO, bCryptPasswordEncoder);
        userRepository.save(userEntity);
    }

    public Long getPreviouslyAddedUserId(String email) {
        return userRepository.findIdByEmail(email).orElseThrow(() ->
                new UserIdNotFoundException("User id not found"));
    }

    /**
     * retrieve the currently logged-in user details
     *
     * @return id of currently logged-in user
     * @throws UserIdNotFoundException if user id will not be found
     */
    public Long getCurrentLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findIdByEmail(email).orElseThrow(() ->
                new UserIdNotFoundException("User id not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }
}


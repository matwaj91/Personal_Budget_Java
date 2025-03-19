package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.mapper.UserMapper;
import PersonalBudget.business.user.domain.model.UserAccountEntity;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.domain.service.exception.EmailAlreadyConfirmedException;
import PersonalBudget.business.user.domain.service.exception.TokenNotFoundException;
import PersonalBudget.business.user.domain.service.exception.UserNotFoundException;
import PersonalBudget.business.user.dto.UserDTO;
import PersonalBudget.business.user.dto.UserEmailDTO;
import PersonalBudget.business.user.dto.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static PersonalBudget.common.util.TokenGenerator.getGeneratedToken;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean isEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserAccountEntity addNewUser(UserDTO userDTO) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDTO.password());
        UserAccountEntity userEntity = userMapper.mapUserDTOToUserEntity(userDTO, encodedPassword, getGeneratedToken());
        userRepository.save(userEntity);
        return userEntity;
    }

    @Transactional
    public void enableUserAccount(String token) {
        UserAccountEntity userAccount = userRepository.findUserByToken(token).orElseThrow(() ->
                new TokenNotFoundException("Token not found"));
        if (userAccount.isEnabled()) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }
        userRepository.enableUserAccount(userAccount.getEmail());
    }

    public Long getCurrentLoggedInUserId() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findIdByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    public UserProfileDTO getUserProfileDetails() {
        Long loggedInUserId = getCurrentLoggedInUserId();
        return userRepository.findUserProfileDetailsByUserId(loggedInUserId);
    }

    public void updateUserName(UserProfileDTO userProfileDTO) {
        Long loggedInUserId = getCurrentLoggedInUserId();
        String name = userProfileDTO.name();
        userRepository.setUserName(loggedInUserId, name);
    }

    public void updateUserPassword(UserProfileDTO userProfileDTO) {
        Long loggedInUserId = getCurrentLoggedInUserId();
        String encodedPassword = bCryptPasswordEncoder.encode(userProfileDTO.password());
        userRepository.setUserPassword(loggedInUserId, encodedPassword);
    }

    public void updateUserNameAndPassword(UserProfileDTO userProfileDTO) {
        Long loggedInUserId = getCurrentLoggedInUserId();
        String name = userProfileDTO.name();
        String encodedPassword = bCryptPasswordEncoder.encode(userProfileDTO.password());
        userRepository.setUserNameAndPassword(loggedInUserId, name, encodedPassword);
    }

    public void deleteUserAccount(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updatePasswordResetToken(UserEmailDTO userEmailDTO, String passwordResetToken) {
        String email = userEmailDTO.email();
        userRepository.setPasswordResetToken(email, passwordResetToken);
    }

    public Optional<String> isAccount(String token) {
        return userRepository.existsByToken(token);
    }

    public void resetPassword(String email, String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        userRepository.setUserPasswordAfterReset(email, encodedPassword);
    }
}
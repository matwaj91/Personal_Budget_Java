package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.model.User;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.dto.LoginDto;
import PersonalBudget.business.user.dto.RegistrationDto;
import PersonalBudget.business.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean isEmail(UserDto userDto) {
        User user = userRepository
                .findUserByEmail(userDto.getEmail());
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public void addNewUser(RegistrationDto registrationDto) {
        //mapper????????
        User user = new User(
                registrationDto.getName(),
                registrationDto.getEmail(),
                bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        userRepository.save(user);
    }

    public boolean isLoginValid(LoginDto loginDto) {
        User user = userRepository.findUserByEmail(loginDto.getEmail());
        String encodedPassword = user.getPassword();
        String password = loginDto.getPassword();

        if (bCryptPasswordEncoder.matches(password, encodedPassword)) {
            Optional<User> optionalUser =
                    userRepository.findUserByEmailAndPassword(loginDto.getEmail(), encodedPassword);
            if (optionalUser.isPresent()) {
                return true;
            }
        }
        return false;
    }
}


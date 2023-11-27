package PersonalBudget.business.user.domain.service;

import PersonalBudget.business.user.domain.model.User;
import PersonalBudget.business.user.domain.repository.UserRepository;
import PersonalBudget.business.user.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean isEmail(RegistrationDto registrationDto) {
        Optional<User> optionalUser = userRepository
                .findUserByEmail(registrationDto.getEmail());
        if (optionalUser.isPresent()) {
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
                bCryptPasswordEncoder.encode(registrationDto.getPassword())
        );
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }
}


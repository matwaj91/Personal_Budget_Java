package PersonalBudget.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static PersonalBudget.common.security.UserRole.ADMIN;
import static PersonalBudget.common.security.UserRole.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfig { //mechanizm Spring Security oparty jest na filtrach

    @Bean
    public SecurityFilterChain basicAuthenticationSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/", "/api/v1/login", "/api/v1/signup").permitAll()
                        //.requestMatchers("/api/v1/menu", "/api/v1/success").hasRole(USER.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails firstUser = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles(String.valueOf(ADMIN)) //ROLE_ADMIN
                .build();

        UserDetails secondUser = User.withUsername("user2")
                .password(passwordEncoder().encode("password2"))
                .roles(String.valueOf(USER)) //ROLE_USER
                .build();

        return new InMemoryUserDetailsManager(firstUser, secondUser);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

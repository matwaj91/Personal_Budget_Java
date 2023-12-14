package PersonalBudget.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String URL = "/api/v1";

    @Bean
    public SecurityFilterChain basicAuthenticationSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(http -> http.requestMatchers("/",
                            URL + "/login",
                            URL + "/signup",
                            "/css/**",
                            "/js/**",
                            "/img/**",
                            URL + "/signup/success",
                            URL + "/password/forgot")
                            .permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(formLogin -> formLogin.loginPage(URL + "/login")
                            .permitAll()
                            .defaultSuccessUrl(URL + "/menu", true)
                            .failureUrl(URL + "/login?error")
                            .usernameParameter("email")
                            .passwordParameter("password")
                    )
                    .logout(logout -> logout.logoutUrl(URL + "/logout")
                            .deleteCookies("JSESSIONID", "remember-me")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .logoutSuccessUrl(URL + "/login?logout")

                    )
                    .rememberMe(remember -> remember.key("uniqueAndSecret")
                            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                    );
        return  httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

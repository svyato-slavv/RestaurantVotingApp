package ru.ivanov.restaurantvotingapplication.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.ivanov.restaurantvotingapplication.model.Role;
import ru.ivanov.restaurantvotingapplication.model.User;
import ru.ivanov.restaurantvotingapplication.repository.UserRepository;
import ru.ivanov.restaurantvotingapplication.web.AuthUser;

import java.util.Optional;


@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final UserRepository userRepository;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
            return new AuthUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
        };
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**").authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/profile").anonymous()
                        .requestMatchers("/api/**").authenticated()
                ).httpBasic(Customizer.withDefaults())
                .sessionManagement(smc -> smc
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}



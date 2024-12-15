package pl.galushop.GaluShop.configuration;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()

        );
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/loginEmployee", "/loginUser", "/registerEmployee", "/registerUser", "/CSS/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/loginUser")
                        .defaultSuccessUrl("/userPanel")
                        .permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/loginEmployee")
                        .defaultSuccessUrl("/employeePanel")
                        .permitAll()

                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

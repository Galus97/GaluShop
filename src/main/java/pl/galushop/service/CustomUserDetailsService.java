package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.galushop.component.CurrentUser;
import pl.galushop.component.ErrorMessages;
import pl.galushop.component.MessageService;
import pl.galushop.model.User;
import pl.galushop.repository.UserRepository;
import pl.galushop.util.ServiceValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        serviceValidator.throwIfEmailIsInvalid(email, ErrorMessages.EMAIL_IS_INVALID);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(messageService.getMessage(
                        ErrorMessages.USER_OR_EMPLOYEE_NOT_FOUND, email)));

            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toList());

            return new CurrentUser(
                    user.getEmail(),
                    user.getPassword(),
                    authorities,
                    user);
    }
}

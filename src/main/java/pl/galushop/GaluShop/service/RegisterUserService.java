package pl.galushop.GaluShop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
@RequiredArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private List<String> validateUserErrors(User user){
        List<String> errors = null;
        Optional<User> userExistByEmail = userRepository.findByEmail(user.getEmail());
        if(userExistByEmail.isPresent()){
            errors = new ArrayList<>();
            errors.add("Ten adres email jest już używany. Wpisz inny adres email");
        }
        return errors;
    }

    private User saveNewUserToDatabase(User user) throws Exception{
        List<String> validationFailures = validateUserErrors(user);
        if(validationFailures.isEmpty()){
            user.setUserId(null);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new Exception();
        }
    }
}

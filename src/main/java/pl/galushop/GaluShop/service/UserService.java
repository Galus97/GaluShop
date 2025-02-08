package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(Long userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + userId + " not found"));
    }

    public void deleteUser(Long userId){
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + userId + " not found"));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUser(User user){
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + user.getUserId() + " not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(existingUser);
    }
}

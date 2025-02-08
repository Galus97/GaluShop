package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.dto.UserRequest;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + userId + " not found"));
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + userId + " not found"));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUser(UserRequest userRequest){
        User existingUser = userRepository.findById(userRequest.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User with ID " + userRequest.getUserId() + " not found"));

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());
        if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()){
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        userRepository.save(existingUser);
    }
}

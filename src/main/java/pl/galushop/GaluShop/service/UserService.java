package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserById(Long userId){
        if(userId == null || userId < 0){
            throw new IllegalArgumentException("User Id is invalid");
        }
        return userRepository.findByUserId(userId);
    }

    public void deleteUserFromDatabase(User user){
        if(user != null){
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("User is null");
        }
    }

    public void updateUser(Long userId, User user){
        if(userId != null && userId > 0 && user != null){
            userRepository.updateUserByUserId(userId,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword()
            );
        } else {
            throw new IllegalArgumentException("User Id is invalid");
        }
    }
}

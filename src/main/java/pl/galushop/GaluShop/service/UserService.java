package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long userId){
        if(userId != null && userId < 0){
            if(userRepository.findByUserId(userId).isPresent()){
                return userRepository.findByUserId(userId).get();
            }
            throw new NoSuchElementException("This User doesn't exist in Data base");
        }
        throw new IllegalArgumentException("User Id is invalid");
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

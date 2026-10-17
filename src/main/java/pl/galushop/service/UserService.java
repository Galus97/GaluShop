package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.dto.response.UserResponse;
import pl.galushop.mapper.UserMapper;
import pl.galushop.model.User;
import pl.galushop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserResponse(User user) {
        return UserMapper.toUserResponse(user);
    }


}

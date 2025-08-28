package iuh.fit.se.tutorial.service.serviceimpl;

import iuh.fit.se.tutorial.dto.request.UserCreationRequest;
import iuh.fit.se.tutorial.dto.request.UserUpdateRequest;
import iuh.fit.se.tutorial.dto.response.UserResponse;
import iuh.fit.se.tutorial.entity.User;
import iuh.fit.se.tutorial.exception.AppException;
import iuh.fit.se.tutorial.entity.enums.ErrorCode;
import iuh.fit.se.tutorial.mapper.UserMapper;
import iuh.fit.se.tutorial.repository.UserRepository;
import iuh.fit.se.tutorial.service.UserService;
import iuh.fit.se.tutorial.utils.PWEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;
    public User createUser(UserCreationRequest userCreationRequest) {
        if(userRepository.existsByUsername(userCreationRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(PWEncoder.get().encode(userCreationRequest.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}

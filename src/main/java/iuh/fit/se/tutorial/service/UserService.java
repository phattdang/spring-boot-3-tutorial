package iuh.fit.se.tutorial.service;

import iuh.fit.se.tutorial.dto.request.UserCreationRequest;
import iuh.fit.se.tutorial.dto.request.UserUpdateRequest;
import iuh.fit.se.tutorial.dto.response.UserResponse;
import iuh.fit.se.tutorial.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserCreationRequest userCreationRequest);
    List<User> getUsers();
    UserResponse getUser(String userId);
    UserResponse updateUser(String userId, UserUpdateRequest request);
    void deleteUser(String userId);
}

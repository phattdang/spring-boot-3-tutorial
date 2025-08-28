package iuh.fit.se.L09_SpringSecurity.service;

import iuh.fit.se.L09_SpringSecurity.dto.request.UserCreationRequest;
import iuh.fit.se.L09_SpringSecurity.dto.request.UserUpdateRequest;
import iuh.fit.se.L09_SpringSecurity.dto.response.UserResponse;
import iuh.fit.se.L09_SpringSecurity.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserCreationRequest userCreationRequest);
    List<User> getUsers();
    UserResponse getUser(String userId);
    UserResponse updateUser(String userId, UserUpdateRequest request);
    void deleteUser(String userId);
}

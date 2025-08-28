package iuh.fit.se.L09_SpringSecurity.controller;

import iuh.fit.se.L09_SpringSecurity.dto.request.UserCreationRequest;
import iuh.fit.se.L09_SpringSecurity.dto.request.UserUpdateRequest;
import iuh.fit.se.L09_SpringSecurity.dto.response.ApiResponse;
import iuh.fit.se.L09_SpringSecurity.dto.response.UserResponse;
import iuh.fit.se.L09_SpringSecurity.entity.User;
import iuh.fit.se.L09_SpringSecurity.entity.enums.ErrorCode;
import iuh.fit.se.L09_SpringSecurity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    @PostMapping
//    User createUser(@RequestBody @Valid UserCreationRequest request){
//        return userService.createUser(request);
//    }

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<User>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}

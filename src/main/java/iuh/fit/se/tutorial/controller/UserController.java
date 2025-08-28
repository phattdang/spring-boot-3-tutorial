package iuh.fit.se.tutorial.controller;

import iuh.fit.se.tutorial.dto.request.UserCreationRequest;
import iuh.fit.se.tutorial.dto.request.UserUpdateRequest;
import iuh.fit.se.tutorial.dto.response.ApiResponse;
import iuh.fit.se.tutorial.dto.response.UserResponse;
import iuh.fit.se.tutorial.entity.User;
import iuh.fit.se.tutorial.entity.enums.ErrorCode;
import iuh.fit.se.tutorial.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

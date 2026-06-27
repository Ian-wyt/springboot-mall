package com.ian.springbootmall.controller;

import com.ian.springbootmall.dto.UserLoginRequest;
import com.ian.springbootmall.dto.UserRegisterRequest;
import com.ian.springbootmall.model.User;
import com.ian.springbootmall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User Controller", description = "APIs for user registration and login")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register user")
    @PostMapping("/users/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        Integer userId = userService.createUser(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "User login")
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}

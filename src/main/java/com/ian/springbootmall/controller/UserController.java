package com.ian.springbootmall.controller;

import com.ian.springbootmall.dto.UserLoginRequest;
import com.ian.springbootmall.dto.UserRegisterRequest;
import com.ian.springbootmall.model.User;
import com.ian.springbootmall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User Controller", description = "用戶註冊登入相關的 API")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用戶註冊")
    @PostMapping("/users/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        Integer userId = userService.createUser(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "用戶登入")
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}

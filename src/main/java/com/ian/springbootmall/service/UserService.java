package com.ian.springbootmall.service;

import com.ian.springbootmall.dto.UserRegisterRequest;
import com.ian.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}

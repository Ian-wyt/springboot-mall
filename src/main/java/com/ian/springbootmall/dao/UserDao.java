package com.ian.springbootmall.dao;

import com.ian.springbootmall.dto.UserLoginRequest;
import com.ian.springbootmall.dto.UserRegisterRequest;
import com.ian.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}

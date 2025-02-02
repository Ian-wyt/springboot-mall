package com.ian.springbootmall.dao;

import com.ian.springbootmall.dto.UserRegisterRequest;
import com.ian.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}

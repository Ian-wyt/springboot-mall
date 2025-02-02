package com.ian.springbootmall.service.impl;

import com.ian.springbootmall.dao.UserDao;
import com.ian.springbootmall.dto.UserRegisterRequest;
import com.ian.springbootmall.model.User;
import com.ian.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        // 確認email是否被註冊過
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        // 回傳400錯誤碼，前端的請求參數有誤
        if (user != null) {
            log.warn("The email {} is already registered.", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 註冊帳號
        return userDao.createUser(userRegisterRequest);
    }
}

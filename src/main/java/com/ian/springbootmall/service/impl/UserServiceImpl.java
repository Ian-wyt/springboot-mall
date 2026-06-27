package com.ian.springbootmall.service.impl;

import com.ian.springbootmall.dao.UserDao;
import com.ian.springbootmall.dto.UserLoginRequest;
import com.ian.springbootmall.dto.UserRegisterRequest;
import com.ian.springbootmall.model.User;
import com.ian.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
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
        // Check whether the email has already been registered.
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        // Return 400 when the client request parameters are invalid.
        if (user != null) {
            log.warn("The email {} is already registered.", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Use MD5 to generate the password hash value.
        String hashPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashPassword);

        // Register the account.
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        // Get user data.
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // Check whether the user exists.
        if (user == null) {
            log.warn("The email {} is not registered.", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Check whether the password is correct.
        String hashPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        if (hashPassword.equals(user.getPassword())) {
            return user;
        } else {
            log.warn("The password of email {} is not correct.", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}

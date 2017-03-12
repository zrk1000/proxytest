package com.zrk1000.proxytest.service;

import com.zrk1000.proxytest.mapper.UserMapper;
import com.zrk1000.proxytest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rongkang on 2017-03-11.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser(String name) {
        return userMapper.findUserInfo();
    }
}

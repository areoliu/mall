package com.mall.user.service;


import com.mall.user.dao.UserMapper;
import com.mall.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User getUser(Long id) {
        return userMapper.selectById(id);
    }
}

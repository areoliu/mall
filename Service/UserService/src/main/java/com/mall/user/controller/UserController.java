package com.mall.user.controller;

import com.alibaba.fastjson.JSON;
import com.mall.user.dao.UserMapper;
import com.mall.user.entity.User;
import com.mall.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/user/{id}")
    public String test(@PathVariable Long id){
        User user = userService.getById(id);
        return JSON.toJSONString(user);


    }

}

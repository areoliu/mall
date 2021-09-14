package com.mall.user.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.user.dao.UserMapper;
import com.mall.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements IUserService {

}

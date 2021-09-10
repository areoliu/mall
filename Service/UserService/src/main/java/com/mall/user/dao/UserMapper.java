package com.mall.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.MatchesPattern;

public interface UserMapper extends BaseMapper<User> {
}

package com.example.mybackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybackend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

package com.example.mybackend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybackend.dto.UserCreateDTO;
import com.example.mybackend.dto.UserUpdateDTO;
import com.example.mybackend.entity.User;

public interface UserService extends IService<User> {

    User create(UserCreateDTO dto);

    User update(Long id, UserUpdateDTO dto);

    IPage<User> page(int current, int size, String keyword);
}

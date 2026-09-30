package com.example.mybackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybackend.dto.UserCreateDTO;
import com.example.mybackend.dto.UserUpdateDTO;
import com.example.mybackend.entity.User;
import com.example.mybackend.mapper.UserMapper;
import com.example.mybackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(UserCreateDTO dto) {
        long count = count(new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
        if (count > 0) {
            throw new IllegalArgumentException("用户名已存在: " + dto.getUsername());
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        // 密码加密后再存，不存明文
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setStatus(1);
        save(user);
        return user;
    }

    @Override
    public User update(Long id, UserUpdateDTO dto) {
        User user = getById(id);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在: id=" + id);
        }
        if (dto.getNickname() != null) user.setNickname(dto.getNickname());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getStatus() != null) user.setStatus(dto.getStatus());
        updateById(user);
        return user;
    }

    @Override
    public IPage<User> page(int current, int size, String keyword) {
        Page<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(User::getUsername, keyword)
                   .or().like(User::getNickname, keyword);
        }
        wrapper.orderByDesc(User::getId);
        return page(page, wrapper);
    }
}

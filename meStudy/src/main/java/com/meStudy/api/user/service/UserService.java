package com.meStudy.api.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meStudy.api.user.entity.User;
import com.meStudy.api.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {}

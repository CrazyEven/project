package com.personal.project.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.project.mapper.UserMapper;
import com.personal.project.pojo.User;
import com.personal.project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    StringBuilder stringBuilder=new StringBuilder();

    List<String> list=new ArrayList<>();
    Set<String> set=new HashSet<>();
    StringBuffer stringBuffer=new StringBuffer();
}

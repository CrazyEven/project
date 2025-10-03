package com.personal.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personal.project.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

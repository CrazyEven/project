package com.personal.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.personal.project.exception.BusinessException;
import com.personal.project.pojo.User;
import com.personal.project.response.RespBody;
import com.personal.project.response.RespCode;
import com.personal.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("hello")
    public String sayHello(@RequestParam String name) {
        return "Hello" + name;
    }

    @Operation(summary = "保存用户")
    @PostMapping("save")
    public RespBody add(@RequestBody User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, user.getName());
        long count = userService.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(RespCode.USER_EXIST);
        }
        userService.save(user);
        return RespBody.success();
    }

    @Operation(summary = "获取用户列表")
    @PostMapping("getUsers")
    public RespBody<PageInfo<User>> getUsers(@RequestBody User user, @RequestParam int page, @RequestParam int size) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.like(user.getName()!=null,User::getName,user.getName());
        PageHelper.startPage(page, size);
        List<User> list = userService.list(queryWrapper);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return RespBody.success(pageInfo);
    }

    @Operation(summary = "删除用户")
    @PostMapping("delUser")
    public RespBody del(@RequestParam int id){
        userService.removeById(id);
        return RespBody.success();
    }

}

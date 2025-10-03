package com.personal.project.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
@Schema(description = "用户信息")
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private int roleType;
    private String isValid;
    private Date createTime;
}

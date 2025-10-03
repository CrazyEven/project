package com.personal.project.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RespCode {

    SUCCESS(200,"SUCCESS"),
    ERROR(500,"FAIL"),
    USER_EXIST(10001, "用户名已存在");

    private final Integer code;
    private final String message;
}

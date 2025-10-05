package com.personal.project.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessErrorCode {
    // 系统错误
    SYSTEM_ERROR("SYS_001", "系统内部错误"),
    DATABASE_ERROR("SYS_002", "数据库操作失败"),
    // 业务错误
    PARAM_INVALID("BIZ_001", "参数无效"),
    USER_NOT_FOUND("BIZ_002", "用户不存在"),
    ORDER_NOT_FOUND("BIZ_003", "订单不存在"),
    INSUFFICIENT_BALANCE("BIZ_004", "余额不足"),

    // 权限错误
    UNAUTHORIZED("AUTH_001", "未授权访问"),
    TOKEN_EXPIRED("AUTH_002", "令牌已过期");

    private final String code;
    private final String message;

    // 根据错误码获取枚举
    public static BusinessErrorCode getByCode(String code) {
        for (BusinessErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return SYSTEM_ERROR; // 默认返回系统错误
    }
}

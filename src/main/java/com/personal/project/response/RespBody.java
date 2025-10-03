package com.personal.project.response;

import lombok.Getter;

@Getter
public class RespBody<T> {
    private Integer code;
    private String message;
    private T data;


    private RespBody(Integer code) {
        this.code = code;
    }

    private RespBody(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private RespBody(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RespBody<T> success() {
        return new RespBody<T>(RespCode.SUCCESS.getCode());
    }

    public static <T> RespBody<T> success(String message) {
        return new RespBody<T>(RespCode.SUCCESS.getCode(),message);
    }

    public static <T> RespBody<T> success(T data) {
        return new RespBody<T>(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMessage(), data);
    }

    public  static <T> RespBody<T> fail() {
        return new RespBody<T>(RespCode.ERROR.getCode());
    }

    public  static <T> RespBody<T> fail(String message) {
        return new RespBody<T>(RespCode.ERROR.getCode(), message);
    }

    public static <T> RespBody<T> fail(RespCode responseCode) {
        return new RespBody<T>(RespCode.ERROR.getCode(), responseCode.getMessage());
    }

    public static <T> RespBody<T> fail(T data) {
        return new RespBody<T>(RespCode.ERROR.getCode(), RespCode.ERROR.getMessage(), data);
    }
}

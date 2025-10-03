package com.personal.project.exception;

import com.personal.project.response.RespCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(RespCode respCode) {
        this.code = respCode.getCode();
        this.message = respCode.getMessage();
    }
}

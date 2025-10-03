package com.personal.project.exception;

import com.personal.project.response.RespBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBody handleException(Exception e) {
        e.printStackTrace();
        return RespBody.fail(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public RespBody handleException(BusinessException e) {
        e.printStackTrace();
        return RespBody.fail(e.getMessage());
    }
}

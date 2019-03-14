package com.demo.exception;

import lombok.Data;

/**
 * 业务异常类
 */
@Data
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(String errmsg) {
        super(errmsg);
        this.code = -1;
        this.message = errmsg;
    }

    public BusinessException(int code, String errmsg) {
        super(errmsg);
        this.code = code;
        this.message = errmsg;
    }

    @Override
    public String toString() {
        return "{" + "\"code\":" + code + ", \"message\":\"" + message + "\"" + "}";
    }
}

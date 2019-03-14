package com.demo.common;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义响应结构
 */
@Data
@NoArgsConstructor
public class Result<T> {

    // 响应业务状态
    private int status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T data;

    /**
     * 构建响应
     * @param status
     * @return
     */
    public static Result build(int status) {
        return new Result(status, null, null);
    }

    /**
     * 构建响应
     * @param status
     * @param msg
     * @return
     */
    public static Result build(int status, String msg) {
        return new Result(status, msg, null);
    }

    /**
     * 构建响应，带数据
     * @param status
     * @param msg
     * @param data
     * @return
     */
    public static <T> Result build(int status, String msg, T data) {
        return new Result(status, msg, data);
    }

    /**
     * 构建成功响应
     * @return
     */
    public static Result ok() {
        return new Result(200, "OK", null);
    }

    /**
     * 构建成功响应，带数据
     * @param data
     * @return
     */
    public static <T> Result ok(T data) {
        return new Result(200, "OK", data);
    }

    /**
     * 构造方法
     * @param status
     * @param msg
     * @param data
     */
    public Result(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 通过feign调用回来后的data是LinkedHashMap，需要转换
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getData(Class<T> clazz) {
        if (data.getClass() == clazz) {
            return (T) data;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(data, clazz);
    }

    /**
     * 通过feign调用回来后的data是LinkedHashMap，需要转换
     * @param collectionClazz
     * @param elementClazz
     * @param <T>
     * @return
     */
    public <T> T getData(Class<T> collectionClazz, Class<T> elementClazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz,
            elementClazz);
        return objectMapper.convertValue(data, javaType);
    }

}

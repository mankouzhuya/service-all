package com.github.zyj.serviceall.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应载体
 * @author jun
 * @param <T>
 *
 */
@Data
public class ResponseDTO<T> implements Serializable {

    private T data;

    private Integer code;

    private String msg;
}

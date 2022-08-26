package com.github.zyj.serviceall.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String msg;
}

package com.github.zyj.serviceall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误枚举
 *
 * @author jun
 */
@AllArgsConstructor
@Getter
public enum ErrorEnums {

    BIZ_ERROR(1000, "业务异常"),
    UNKNOW_ERROR(1001, "未知错误"),
    PARAMETER_IS_NULL(1002, "参数为空"),
    RETURN_IS_NULL(1003, "方法返回值为空"),

    ;
    private Integer code;
    private String msg;
}

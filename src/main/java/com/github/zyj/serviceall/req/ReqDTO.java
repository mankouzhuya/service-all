package com.github.zyj.serviceall.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReqDTO implements Serializable {

    @NotBlank(message = "name不能为空")
    private String name;

    @NotNull(message = "age不能为空")
    private Integer age;

}

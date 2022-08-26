package com.github.zyj.serviceall.service;

import com.github.zyj.serviceall.req.ReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class UserService {

    @Autowired
    private UserService2 userService2;

    public void test(@Valid @NotNull(message = "reqDTO不能为空") ReqDTO reqDTO){
        @Valid @NotNull ReqDTO dto = userService2.test2();
//        Assert.notNull(dto,"返回值不能为空");



        List<String> tutorialsList = Arrays.asList("Java", null,"","HTML");
        String tutorials = tutorialsList.stream()
                .map((@Validated @NotNull(message = "入参不能为空") var tutorial) -> tutorial.toUpperCase())
                .collect(Collectors.joining(", "));

        System.out.println(tutorials);

        System.out.println(reqDTO);
    }
}

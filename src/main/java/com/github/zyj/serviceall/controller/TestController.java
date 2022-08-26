package com.github.zyj.serviceall.controller;

import com.github.zyj.serviceall.exception.BizException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/admin")
    public Mono<String> admin() {
        return Mono.just("you are admin");
    }

    @GetMapping("/user")
    public Mono<Map> user() {
        throw new BizException(1,"aaa");
//        Map map = new HashMap();
//        map.put("msg","you are user");
//        return Mono.just(map);
    }
}

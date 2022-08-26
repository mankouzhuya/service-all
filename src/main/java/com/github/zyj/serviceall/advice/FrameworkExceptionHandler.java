package com.github.zyj.serviceall.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zyj.serviceall.jwt.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class FrameworkExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof InvalidJwtAuthenticationException) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            Map map = new HashMap();
            map.put("认证异常", "aaa");
            try {
                var body = exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(map));
                return exchange.getResponse().writeWith(Mono.just(body));
            } catch (JsonProcessingException e) {
                log.error("序列化异常:{}", e);
            }
        }

        return null;
    }

}

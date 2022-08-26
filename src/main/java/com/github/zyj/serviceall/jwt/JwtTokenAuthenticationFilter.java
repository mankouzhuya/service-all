package com.github.zyj.serviceall.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JwtTokenAuthenticationFilter implements WebFilter {

    public static final String HEADER_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;

    public static String AUTHENTICATION_KEY = "app_Authentication_info";

    public JwtTokenAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //OPTIONS 请求不处理
        if (exchange.getRequest().getMethod().equals(HttpMethod.OPTIONS)) {
            return exchange.getResponse().setComplete();
        }
        //get token
        String token = resolveToken(exchange.getRequest());
        //校验token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            //认证token
            Authentication authentication = tokenProvider.getAuthentication(token);
            exchange.getAttributes().put(AUTHENTICATION_KEY,authentication);
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }
        return chain.filter(exchange);
    }

    /**
     * 从http head中获取token
     *
     * @param request
     * @return
     */
    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
package com.github.zyj.serviceall.filter;

import com.github.zyj.serviceall.jwt.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.github.zyj.serviceall.jwt.JwtTokenAuthenticationFilter.AUTHENTICATION_KEY;

public class TokenRefreshFilter implements WebFilter {


    private JwtTokenProvider jwtTokenProvider;

    public TokenRefreshFilter(JwtTokenProvider tokenProvider) {
        if (tokenProvider == null) {
            throw new IllegalArgumentException("tokenProvider为空");
        }
        this.jwtTokenProvider = tokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Authentication authentication = exchange.getAttribute(AUTHENTICATION_KEY);
        if (authentication != null) {
            String token = jwtTokenProvider.createFullToken(authentication);
            exchange.getResponse().getHeaders().add(HttpHeaders.AUTHORIZATION, token);
        }
        return chain.filter(exchange);
    }

}

package com.github.zyj.serviceall.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import static java.util.stream.Collectors.joining;

@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "roles";

    private final JwtProperties jwtProperties;

    private String secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    /**
     * 创建token
     *
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Claims claims = Jwts.claims().setSubject(username);
        if (!authorities.isEmpty()) {
            claims.put(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        }
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.jwtProperties.getValidityInMs());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    /**
     * 创建完整的token
     * @param authentication
     * @return
     */
    public String createFullToken(Authentication authentication) {
        return JwtTokenAuthenticationFilter.HEADER_PREFIX + createToken(authentication);
    }


    /**
     * token 认证,置换Authentication
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        Object authoritiesClaim = claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * 校验token 是否有效
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

}

package com.github.zyj.serviceall.controller;

import com.github.zyj.serviceall.jwt.JwtTokenProvider;
import com.github.zyj.serviceall.req.ReqDTO;
import com.github.zyj.serviceall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthRequest request) {
        ReqDTO reqDTO = new ReqDTO();
        reqDTO.setName("上海");
        reqDTO.setAge(1);
        userService.test(reqDTO);

        String username = request.getUsername();
        Mono<Authentication> authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

        return authentication.map(auth -> jwtTokenProvider.createToken(auth));
    }
}

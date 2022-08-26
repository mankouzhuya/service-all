package com.github.zyj.serviceall.security;

import com.github.zyj.serviceall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"))
        );
    }
}
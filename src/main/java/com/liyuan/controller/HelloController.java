package com.liyuan.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/test")
    public String test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        StringBuilder info = new StringBuilder();
        info.append(authentication.getName()).append("\n")
                .append(authentication.getPrincipal()).append("\n")
                .append(authentication.getCredentials()).append("\n")
                .append(authentication.getAuthorities())
                .append("\n").append(authentication.getDetails());
        return info.toString();
    }
}

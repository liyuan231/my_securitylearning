package com.liyuan.controller;

import com.liyuan.entity.Rest;
import com.liyuan.entity.RestBody;
import com.liyuan.entity.SysUser;
import com.liyuan.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/failure")
    public Rest<?> loginFailure() {
        return RestBody.failure(HttpStatus.UNAUTHORIZED.value(), "登陆失败!");
    }

    @PostMapping("/success")
    public Rest<?> loginSuccess() {
        SysUser principal = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return RestBody.okData(principal.toString(), "登陆成功！");
    }

}

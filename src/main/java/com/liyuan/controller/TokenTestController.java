package com.liyuan.controller;

import com.liyuan.entity.Rest;
import com.liyuan.entity.RestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenTestController {

    @PreAuthorize("hasAuthority('/test')")
    @GetMapping("/tokenTest")
    public Rest<?> tokenTest() {
        return RestBody.ok("访问com.liyuan.controller.TokenTestController.tokenTest成功！");
    }
}

package com.liyuan.component.jwt;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * jwt payload
 */
public class JwtPayloadBuilder {
    private Map<String, String> payload = new HashMap<>();
    private Map<String, String> additional;
    /**
     * 签发
     */
    private String issuer;
    /**
     * 订阅主体
     */
    private String subscriber;
    /**
     * jwt发给谁
     */
    private String audience;
    /**
     * 多久过期
     */
    private LocalDateTime expiration;
    /**
     * 签发时间
     */
    private LocalDateTime issueAt = LocalDateTime.now();
    /**
     * 权限集
     */
    private Set<String> authorities = new HashSet<>();
    /**
     * jwt唯一身份标识，作为一次性token，避免重放攻击
     */
    private String jti = String.valueOf(System.currentTimeMillis());

    public JwtPayloadBuilder issuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public JwtPayloadBuilder subscriber(String subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public JwtPayloadBuilder audience(String audience) {
        this.audience = audience;
        return this;
    }

    public JwtPayloadBuilder authorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    public JwtPayloadBuilder additional(Map<String, String> additional) {
        this.additional = additional;
        return this;
    }

    public JwtPayloadBuilder expirationDays(int days) {
        Assert.isTrue(days > 0, "expiration days should be positive!");
        this.expiration = this.issueAt.plusDays(days);
        return this;
    }

    public String builder() {
        payload.put("issuer", issuer);
        payload.put("subscriber", subscriber);
        payload.put("audience", audience);
        payload.put("expiration", this.expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payload.put("issueAt", issueAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        payload.put("authorities", JSONObject.toJSONString(authorities));
        if(!CollectionUtils.isEmpty(additional)){
            payload.putAll(additional);
        }
        return JSONObject.toJSONString(payload);
    }


}

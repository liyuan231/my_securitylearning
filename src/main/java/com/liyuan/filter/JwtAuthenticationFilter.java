package com.liyuan.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyuan.component.jwt.JwtTokenGenerator;
import com.liyuan.entity.SysUser;
import com.liyuan.handler.SimpleAuthenticationEntryPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHENTICATION_PREFIX = "Bearer ";
    private AuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
    private JwtTokenGenerator jwtTokenGenerator;

    public JwtAuthenticationFilter(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * SecurityContext上下文已有身份认证
         */
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(AUTHENTICATION_PREFIX)) {
            String jwtToken = header.substring(AUTHENTICATION_PREFIX.length());
            if (StringUtils.hasText(jwtToken)) {
                try {
                    authenticationTokenHandle(jwtToken, request);
                } catch (AuthenticationException authenticationException) {
                    authenticationEntryPoint.commence(request, response, authenticationException);
                }
            } else {
                authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is missing!"));
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticationTokenHandle(String jwtToken, HttpServletRequest request) {
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(jwtToken);
        if (Objects.nonNull(jsonObject)) {
            String username = jsonObject.getString("audience");
            JSONArray authoritiesJSONArray = jsonObject.getJSONArray("authorities");
            Iterator<Object> iterator = authoritiesJSONArray.iterator();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            while (iterator.hasNext()) {
                String next = (String) iterator.next();
                authorities.add((GrantedAuthority) () -> next);
            }
            User user = new User(username,"[PASSWORD]",authorities);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,authorities);
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }else {
            throw new BadCredentialsException("token is invalid!");
        }
    }
}

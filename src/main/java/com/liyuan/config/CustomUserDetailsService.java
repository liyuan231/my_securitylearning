package com.liyuan.config;

import com.liyuan.entity.SysUser;
import com.liyuan.service.AuthorityService;
import com.liyuan.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    AuthorityService authorityService;

    /**
     * 当我写道此处时，我发现在普通登录下，必须username唯一，不然为什么会有loadUserByUsername，
     * 即一般登录：username为主键
     * 第三方登录：userId为逐渐
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.loadUserByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        Set<GrantedAuthority> authorities = authorityService.retrieveAuthoritiesByRoleId(sysUser.getRoleId());
        sysUser.setAuthorities(authorities);
        return sysUser;
    }
}

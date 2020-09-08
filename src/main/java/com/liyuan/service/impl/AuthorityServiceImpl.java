package com.liyuan.service.impl;

import com.liyuan.mapper.AuthorityMapper;
import com.liyuan.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    AuthorityMapper authorityMapper;
    @Override
    public Set<GrantedAuthority> retrieveAuthoritiesByRoleId(Integer roleId) {
        String[] strings = authorityMapper.retrieveAuthoritiesByRoleId(roleId);
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String string : strings) {
            authorities.add((GrantedAuthority) () -> string);
        }
        return authorities;

    }
}

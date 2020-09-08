package com.liyuan.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public interface AuthorityService {
    Set<GrantedAuthority> retrieveAuthoritiesByRoleId(Integer roleId);
}

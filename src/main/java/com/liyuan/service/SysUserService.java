package com.liyuan.service;

import com.liyuan.entity.SysUser;

public interface SysUserService {

    SysUser loadUserByUsername(String username);

}

package com.liyuan.service.impl;

import com.liyuan.entity.SysUser;
import com.liyuan.mapper.SysUserMapper;
import com.liyuan.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser loadUserByUsername(String username) {
        return sysUserMapper.loadUserByUsername(username);
    }
}

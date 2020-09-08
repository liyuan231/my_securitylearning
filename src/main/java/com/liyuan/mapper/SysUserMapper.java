package com.liyuan.mapper;

import com.liyuan.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper {
    SysUser loadUserByUsername(String username);
}

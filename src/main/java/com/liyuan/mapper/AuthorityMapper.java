package com.liyuan.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthorityMapper {
    String[] retrieveAuthoritiesByRoleId(Integer roleId);
}

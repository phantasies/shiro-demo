package com.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Desc 角色mapper
 * @author fantao
 * @date 2018年5月16日 下午4:30:53
 * @version 
 */
@Mapper
public interface SysUserRoleMapper {

    //    List<SysUserRole> selectByName(@Param("name") String name);

    /**
     * 查询指定角色对应的权限
     * @param name
     * @return
     */
    String selectPermissionsByRoleName(@Param("name") String name);
}

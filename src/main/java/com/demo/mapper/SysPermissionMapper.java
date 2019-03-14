package com.demo.mapper;

import com.demo.model.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Desc 权限mapper
 * @author fantao
 * @date 2018年5月16日 下午4:31:40
 * @version 
 */
@Mapper
public interface SysPermissionMapper {

    /**
     * selectById
     * @param id
     * @return
     */
    SysPermission selectById(@Param("id") Integer id);

}

package com.demo.mapper;

import com.demo.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Desc 管理员mapper
 * @author fantao
 * @date 2018年5月16日 下午4:31:40
 * @version
 */
@Mapper
public interface SysUserMapper {

    /**
     * selectById
     * @param id
     * @return
     */
    SysUser selectById(@Param("id") Integer id);

    /**
     * selectByUsername
     * @param username
     * @return
     */
    SysUser selectByUsername(@Param("username") String username);
}

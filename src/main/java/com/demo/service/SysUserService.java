package com.demo.service;

import com.demo.mapper.SysPermissionMapper;
import com.demo.mapper.SysUserMapper;
import com.demo.mapper.SysUserRoleMapper;
import com.demo.model.SysPermission;
import com.demo.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc SysUserService
 * @author fantao
 * @date 2018年5月16日 下午4:31:40
 * @version
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * selectByUsername
     * @param username
     * @return
     */
    public SysUser selectByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    /**
     * selectPermissionsByRoleName
     * @param name
     * @return
     */
    public String selectPermissionsByRoleName(String name) {
        return sysUserRoleMapper.selectPermissionsByRoleName(name);
    }

    /**
     * selectById
     * @param id
     * @return
     */
    public SysPermission selectById(Integer id) {
        return sysPermissionMapper.selectById(id);
    }

}

package com.demo.model;

import java.util.Date;

import lombok.Data;

/**
 * @Desc SysUserRole
 * @author fantao
 * @date 2018-12-19 15:52
 * @version
 */
@Data
public class SysUserRole {
    private Long id;
    private String name;
    private String permission;
    private Date createTime;
}

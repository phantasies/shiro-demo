package com.demo.model;

import java.util.Date;

import lombok.Data;

/**
 * @Desc SysUser
 * @author fantao
 * @date 2018-12-19 15:52
 * @version
 */
@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String role;
    private Date createTime;
}

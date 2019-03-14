package com.demo.model;

import java.util.Date;

import lombok.Data;

/**
 * @Desc SysPermission
 * @author fantao
 * @date 2018-12-19 15:52
 * @version
 */
@Data
public class SysPermission {

    private Long id;
    private String name;
    private String description;
    private Date createTime;
}

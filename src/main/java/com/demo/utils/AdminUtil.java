package com.demo.utils;

import org.apache.shiro.SecurityUtils;
import org.springframework.util.Assert;

/**
 * @Desc AdminUtil
 * @author fantao
 * @date 2018-12-25 15:41
 * @version
 */
public class AdminUtil {

    /**
     * 获取当前登录的管理员
     * @return
     */
    public static String getOperator() {
        Assert.notNull(SecurityUtils.getSubject(), "login operator is null");
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
}

package com.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.common.Result;
import com.demo.exception.BusinessException;
import com.demo.model.SysUser;
import com.demo.service.SysUserService;
import com.demo.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Desc LoginController
 * @author fantao
 * @date 2018-12-19 15:52
 * @version
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    SysUserService sysUserService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(path = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(String username, String password) {
        Assert.notNull(username, "username.is.empty");
        Assert.notNull(password, "password.is.empty");

        SysUser sysUser = sysUserService.selectByUsername(username);
        if (sysUser == null) {
            throw new BusinessException("user.login.failed");
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //委派给了shiro，通过realm的实现进行鉴权
            subject.login(token);
            List<String> permissions = new ArrayList<>();
            if (sysUser.getRole() != null) {
                String[] roles = StringUtils.split(sysUser.getRole(), ",");
                for (int i = 0; i < roles.length; i++) {
                    String permissionsByRoleName = sysUserService
                        .selectPermissionsByRoleName(roles[i]);
                    if (StringUtils.isNotEmpty(permissionsByRoleName)) {
                        permissions.addAll(Arrays.asList(StringUtils.split(permissionsByRoleName,
                            ",")));
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("permissions", permissions);
            return Result.build(200, "OK", map);
        } catch (IncorrectCredentialsException e) {
            return Result.build(500, "user.login.failed");
        } catch (LockedAccountException e) {
            return Result.build(500, "user.login.failed");
        } catch (AuthenticationException e) {
            return Result.build(500, "user.login.failed");
        } catch (Exception e) {
            Utils.errorStackTrace(e);
        }
        throw new BusinessException("service.system.error");
    }

}

package com.demo.shiro;

import java.util.Arrays;

import com.demo.model.SysUser;
import com.demo.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Desc 认证与授权实现
 * @author fantao
 * @date 2018年5月16日 下午2:56:19
 * @version 
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    @Autowired
    private SysUserService sysUserService;

    /**
     * 授权，为当前登录的Subject查询角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("##################执行Shiro权限授权##################");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        SysUser user = sysUserService.selectByUsername(username);
        if (user.getRole() != null) {
            String[] roles = StringUtils.split(user.getRole(), ",");
            for (int i = 0; i < roles.length; i++) {
                authorizationInfo.addRole(roles[i]);
                String permissions = sysUserService.selectPermissionsByRoleName(roles[i]);
                if (StringUtils.isNotEmpty(permissions)) {
                    authorizationInfo.addStringPermissions(Arrays.asList(StringUtils.split(
                        permissions, ",")));
                }
            }
            return authorizationInfo;
        }
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        System.out.println("##################执行Shiro鉴权##################");
        String username = (String) authenticationToken.getPrincipal();
        SysUser sysUser = sysUserService.selectByUsername(username);
        if (sysUser == null) {
            throw new AccountException("user.login.failed");
        }
        return new SimpleAuthenticationInfo(
            sysUser.getUsername(), sysUser.getPassword(), getName());
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        //重写shiro的密码验证
        setCredentialsMatcher(new CustomCredentialsMatcher());
    }

}

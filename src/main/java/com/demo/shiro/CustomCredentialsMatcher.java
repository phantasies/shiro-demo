package com.demo.shiro;

import com.demo.utils.DesUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @Desc CustomCredentialsMatcher
 * @author fantao
 * @date 2018-05-23 15:02:38
 * @version 
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 密码校验
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        String inPassword = new String(utoken.getPassword());
        String dbPassword = (String) info.getCredentials();
        return this.equals(inPassword, DesUtil.decrypt(dbPassword));
    }

    /**
     * 测试
     * @param argv
     */
    public static void main(String[] argv) {
        String password = "user002";
        System.out.println(DesUtil.encrypt(password));
    }
}

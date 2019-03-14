package com.demo.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.demo.common.Result;
import com.demo.component.LocaleMessage;
import com.demo.component.SpringContextHolder;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.alibaba.fastjson.JSON;

/**
 *
 * @Desc role授权过滤器
 * @author fantao
 * @date 2018年5月24日 下午2:00:04
 * @version 
 */
public class CustomRoleFilter extends AccessControlFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws IOException {

        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            LocaleMessage localeMessage = SpringContextHolder.getBean("localeMessage");
            Result result = Result.build(500, localeMessage.getMessage("user.not.allowed"));
            response.setCharacterEncoding("UTF-8");
            try {
                response.getWriter().print(JSON.toJSONString(result, true));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                   Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] roles = (String[]) mappedValue;

        boolean isPermitted = false;
        if (roles != null && roles.length > 0) {
            if (roles.length == 1) {
                if (subject.hasRole(roles[0])) {
                    isPermitted = true;
                }
            } else {
                for (int i = 0; i < roles.length; i++) {
                    if (subject.hasRole(roles[0])) {
                        isPermitted = true;
                        break;
                    }
                }
            }
        }
        return isPermitted;
    }
}

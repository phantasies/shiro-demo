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
 * @Desc permission授权过滤器
 * @author fantao
 * @date 2018年5月24日 下午2:00:04
 * @version 
 */
public class CustomPermissionFilter extends AccessControlFilter {

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws IOException {

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            LocaleMessage localeMessage = SpringContextHolder.getBean("localeMessage");
            Result result = Result.build(500, localeMessage.getMessage("user.no.permission"));
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
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else {
                if (!subject.isPermittedAll(perms)) {
                    isPermitted = false;
                }
            }
        }
        return isPermitted;
    }
}

package com.demo.shiro;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.demo.common.Result;
import com.demo.component.LocaleMessage;
import com.demo.component.SpringContextHolder;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

/**
 * @Desc 退出登录过滤器
 * @author fantao
 * @date 2018年6月4日 下午5:22:37
 * @version 
 */
public class CustomLogoutFilter extends LogoutFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //在这里执行退出系统前需要清空的数据  
        Subject subject = getSubject(request, response);
        //        String redirectUrl = getRedirectUrl(request, response, subject);
        ServletContext context = request.getServletContext();
        try {
            subject.logout();
            context.removeAttribute("error");
        } catch (SessionException e) {
            e.printStackTrace();
        }
        LocaleMessage localeMessage = SpringContextHolder.getBean("localeMessage");
        Result result = Result.build(500, localeMessage.getMessage("logout.success"));
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().print(JSON.toJSONString(result, true));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}

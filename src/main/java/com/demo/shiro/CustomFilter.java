package com.demo.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.demo.common.Result;
import com.demo.component.LocaleMessage;
import com.demo.component.SpringContextHolder;
import com.demo.utils.Utils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc 登录过滤器
 * @author fantao
 * @date 2018年5月24日 下午2:00:04
 * @version 
 */
public class CustomFilter extends FormAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            if (Utils.isAjax((HttpServletRequest) request)) {
                LocaleMessage localeMessage = SpringContextHolder.getBean("localeMessage");
                Result result = Result.build(500, localeMessage.getMessage("user.need.login"));
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(JSON.toJSONString(result, true));
            } else {
                this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }
}

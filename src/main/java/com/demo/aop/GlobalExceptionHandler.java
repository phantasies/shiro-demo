package com.demo.aop;

import com.demo.common.Result;
import com.demo.component.LocaleMessage;
import com.demo.exception.BusinessException;
import com.demo.utils.FastJsonConvert;
import com.demo.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @desc controller全局异常处理
 * @author fantao
 * @date 2018-12-03 14:12
 * @version
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private LocaleMessage localeMessage;

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * BusinessException handler
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Object businessExceptionHandler(BusinessException e) {
        Utils.errorStackTrace(e);
        String message = localeMessage.getMessage(e.getMessage());
        Result result = Result.build(500, message);
        return FastJsonConvert.convertObjectToJSON(result);
    }

    /**
     * AuthenticationException handler
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Object authenticationExceptionHandler(AuthenticationException e) {
        Utils.errorStackTrace(e);
        String message = localeMessage.getMessage("user.login.failed");
        Result result = Result.build(500, message);
        return FastJsonConvert.convertObjectToJSON(result);
    }

    /**
     * AuthorizationException handler
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Object authorizationException(AuthorizationException e) {
        Utils.errorStackTrace(e);
        String message = localeMessage.getMessage("user.no.permission");
        Result result = Result.build(500, message);
        return FastJsonConvert.convertObjectToJSON(result);
    }

    /**
     * MissingServletRequestParameterException handler
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public Object missingServletRequestParameterException(MissingServletRequestParameterException e) {
        Utils.errorStackTrace(e);
        String message = localeMessage.getMessage("service.invalid.param");
        Result result = Result.build(500, message);
        return FastJsonConvert.convertObjectToJSON(result);
    }

    /**
     * Exception handler
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e) {
        Utils.errorStackTrace(e);
        String message = localeMessage.getMessage(e.getMessage());
        if (StringUtils.isEmpty(message)) {
            message = localeMessage.getMessage("service.system.error");
        }
        Result result = Result.build(500, message);
        return FastJsonConvert.convertObjectToJSON(result);
    }

}

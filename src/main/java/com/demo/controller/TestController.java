package com.demo.controller;

import com.demo.common.Result;
import com.demo.exception.BusinessException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Desc CategoryController
 * @author fantao
 * @date 2018-12-29 15:52
 * @version
 */
@Controller
@RequestMapping
public class TestController {

    /**
     * 查询
     * @return
     */
    @RequestMapping(path = "/api/test/query", method = RequestMethod.POST)
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions("function1_query")
    public Result testQuery() {
        return Result.ok("查询功能授权通过");
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping(path = "/api/test/add", method = RequestMethod.POST)
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions("function1_edit")
    public Result testAdd() {
        return Result.ok("添加功能授权通过");
    }

    /**
     * 测试aop
     * @return
     */
    @RequestMapping(path = "/api/test/error", method = RequestMethod.POST)
    @ResponseBody
    public Result testError() {
        throw new BusinessException("service.system.error");
    }

}

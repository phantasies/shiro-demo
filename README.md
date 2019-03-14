# shiro-demo
基于shiro+session的后台管理系统登录与权限模块

## shiro集成核心类 ##

Authentication: 认证，即根据用户名和密码验证用户是否可以进入系统

Authorization：授权，即根据role和permission验证用户是否有权限调用某个功能

ShiroConfiguration.java 提供shiro全局配置，包括4个过滤器、ehCache缓存bean、realm、AOP支持

CustomFilter.java: 认证过滤器，继承FormAuthenticationFilter，核心调用链：

1. CustomFilter.isAccessAllowed
2. 调用其父类的AuthenticatingFilter.executeLogin
3. 调用subject.login(token)，
4. 调用CustomRealm.doGetAuthenticationInfo, 该方法返回用于认证的数据SimpleAuthenticationInfo
5. 调用CustomCredentialsMatcher.doCredentialsMatch进行密码校验

CustomPermissionFilter.java: 权限过滤器，继承AccessControlFilter，实现isAccessAllowed方法

CustomRoleFilter.java: 角色过滤器，角色是一组权限的组合，在数据库中映射，继承AccessControlFilter，实现isAccessAllowed方法

CustomLogoutFilter.java: 登出过滤器

CustomRealm.java: 认证信息获取、授权信息获取及验证，实现doGetAuthorizationInfo和doGetAuthenticationInfo两个方法，同时初始化一个CredentialsMatcher的实现

CustomCredentialsMatcher.java: 密码校验

## 登录处理 ##
LoginController.java: 
1. 获取用户输入的用户名和密码，进行密码验证：subject.login(token)实际上委派给shiro，通过realm的实现进行鉴权
2. 验证通过，shiro会自动创建session，在cookie中写入JSESSIONID

## 业务逻辑 ##
TestController.java:
1. 浏览器调用接口，携带JSESSIONID
2. @RequiresAuthentication和@RequiresPermissions("xxx")注解会调用CustomFilter和CustomPermissionFilter来进行认证与授权判断，其中xxx是功能名，在数据库中配置
3. 暂未使用角色授权判断


## 示例 ##
登录请求：
```
http://192.168.100.222:8099/api/login
POST: username=user001&password=user001
````
响应：
```
{
    "status": 200,
    "msg": "OK",
    "data": {
        "permissions": [
            "function1_menu",
            "function1_query"
        ]
    }
}
````
调用查询功能function1_query请求
```
http://192.168.100.222:8099/api/test/query
```
响应：
```
{
    "status": 200,
    "msg": "OK",
    "data": "查询功能授权通过"
}
```
调用创建功能function1_edit请求
```
http://192.168.100.222:8099/api/test/add
```
响应：
```
{"msg":"权限不足","status":500}
```
删除cookie:JSESSSIONID=value，再次调用查询功能function1_query请求
```
http://192.168.100.222:8099/api/test/query
```
响应：
```
{
	"msg":"请先登录",
	"status":500
}
```


## 其他 ##
1. 支持多语言
2. 通过AOP捕获Controller抛出的异常，封装为json
3. 数据库建表及示例数据位于sql目录下

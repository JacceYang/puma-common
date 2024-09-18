package com.explorex.puma.common.login;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor{

    private static final String loginPath= "/api/user/login";

    @Resource
    private TokenService tokenService;

    /**
     * 在请求处理之前进行调用（Controller/RestController方法调用之前）
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 执行权限检查、登录验证、缓存处理等
        if (!loginPath.equals( request.getServletPath())) {
            final LoginUser loginUser = tokenService.getLoginUser(request);
            UserContextHolder.setUserInfo(loginUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        UserContextHolder.removeUserInfo();
    }
}
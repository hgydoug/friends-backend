package com.simple.friends.interceptor;

import com.google.gson.Gson;
import com.simple.friends.common.BaseResponse;
import com.simple.friends.common.ErrorCode;
import com.simple.friends.common.ResultUtils;
import com.simple.friends.model.domain.Users;
import com.simple.friends.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 0. 不是访问所有的资源都需要登录，访问登录业务就不需要登录
        log.info("request.getContextPath() = " + request.getContextPath());
        log.info("servletPath = {}" , request.getServletPath());

        // 1. 判断用户是否登录，如果登录，才能访问对应的资源
        Users user =  userService.getLoginUser(request);
        if (user == null) {
            // 没有登录
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            BaseResponse<?> error = ResultUtils.error(ErrorCode.NOT_LOGIN);
            Gson gson = new Gson();
            PrintWriter writer = response.getWriter();
            writer.write(gson.toJson(error));
            writer.flush();
            return false;
        }

        return true;

    }
}

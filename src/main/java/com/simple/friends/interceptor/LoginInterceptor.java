package com.simple.friends.interceptor;

import com.google.gson.Gson;
import com.simple.friends.common.BaseResponse;
import com.simple.friends.common.ErrorCode;
import com.simple.friends.common.ResultUtils;
import com.simple.friends.config.UserInfoContext;
import com.simple.friends.exception.BusinessException;
import com.simple.friends.model.domain.Users;
import com.simple.friends.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author hgy
 * @description 统一登录拦截，利用springmvc的HandlerInterceptor实现。也看了利用Filter实现。
 * 如果preHandle: 出现异常，afterCompletion无法执行
 * 如果handle出现异常，afterCompletion可以执行
 * 如果postHandle出现异常，afterCompletion也可以执行。
 */
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

        // 这里方法中要try-catch异常，不然会出现afterComplete，无法执行
//        if (true) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
//        }

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

        // 返回true 一定要在此处设置threadlocal值
        UserInfoContext.set(user);

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        if (true) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
//        }
        System.out.println("postHandle");
    }

    /**
     * 无法是否存在异常，无论preHandler或者handler处理异常，最终都会执行这个方法，适合资源的释放。
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param ex any exception thrown on handler execution, if any; this does not
     * include exceptions that have been handled through an exception resolver
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        UserInfoContext.remove();
    }
}

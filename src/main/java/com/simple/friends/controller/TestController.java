package com.simple.friends.controller;

import com.simple.friends.common.ErrorCode;
import com.simple.friends.config.UserInfoContext;
import com.simple.friends.exception.BusinessException;
import com.simple.friends.model.domain.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对应的测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/throw")
    public void throwTest() {
        Users loginUserInfo = UserInfoContext.getLoginUserInfo();
        System.out.println("loginUserInfo = " + loginUserInfo);
        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    }

}

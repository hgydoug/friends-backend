package com.simple.friends.config;

import com.simple.friends.model.domain.Users;

public class UserInfoContext {
    private static final ThreadLocal<Users> usersThreadLocal = new ThreadLocal<>();


    public static void set(Users users) {
        usersThreadLocal.set(users);
    }

    public static void remove() {
        usersThreadLocal.remove();
    }


    public static Users getLoginUserInfo() {
        return usersThreadLocal.get();
    }
}

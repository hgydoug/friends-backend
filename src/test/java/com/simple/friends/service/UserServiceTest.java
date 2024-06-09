package com.simple.friends.service;


import com.simple.friends.model.domain.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户服务测试
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    /**
     * 测试添加用户
     */
    @Test
    public void testAddUser() {
        Users user = new Users();
        user.setUsername("simple");
        user.setUserAccount("simple");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png");
        user.setGender(0);
        user.setUserPassword("12345");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    /**
     * 测试更新用户
     */
    @Test
    public void testUpdateUser() {
        Users user = new Users();
        user.setId(2L);
        user.setUsername("simple");
        user.setUserAccount("123");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png");
        user.setGender(0);
        user.setUserPassword("12345");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void deleteUser() {
        Users users = new Users();

        boolean b = userService.removeById(5L);
        System.out.println("删除结果 ： " + b);
    }

    /**
     * 测试删除用户
     */
    @Test
    public void testDeleteUser() {
        boolean result = userService.removeById(2L);
        Assertions.assertTrue(result);
    }


    /**
     * 测试获取用户
     */
    @Test
    public void testGetUser() {
        Users user = userService.getById(2L);
        Assertions.assertNotNull(user);
    }

    /**
     * 测试用户注册
     */
    @Test
    void userRegister() {
        String userAccount = "simple";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "sim";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "simple ";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testSearchUsersByTags() {

        // todo 这个应该mock数据，不然会导致运行测试案例是失败。
//        List<Users> users = userService.searchUsersByTags(Set.of("java","go"));
        List<Users> users = userService.searchUsersByTagsInMem(Set.of(""));
        users.forEach(users1 -> System.out.println("users1 = " + users1));
        // 如果这个为false: 就不异常，如果为true，抛出异常。
        Assertions.assertFalse(CollectionUtils.isEmpty(users));
    }
}
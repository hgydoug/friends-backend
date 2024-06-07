package com.simple.friends.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.friends.contant.UserConstant;
import com.simple.friends.model.domain.Users;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 用户服务
 */
public interface UserService extends IService<Users> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode    星球编号
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    Users userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    Users getSafetyUser(Users originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签查询用户信息
     * @param tags：标签集合
     * @return：用户集合
     */
    List<Users> searchUsersByTags(Set<String> tags);

    List<Users> searchUsersByTagsInMem(Set<String> tags);

    /**
     * 更新用户信息
     * @param users：用户
     * @param request：request
     * @return
     */
    int updateUser(Users users, HttpServletRequest request);


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    Users getCurrentUser(HttpServletRequest request);

    /**
     * 获取登录的用户信息
     * @param request
     * @return
     */
    Users getLoginUser(HttpServletRequest request);
}

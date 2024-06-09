package com.simple.friends.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.friends.common.ErrorCode;
import com.simple.friends.contant.UserConstant;
import com.simple.friends.exception.BusinessException;
import com.simple.friends.model.domain.Users;
import com.simple.friends.service.UserService;
import com.simple.friends.mapper.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.simple.friends.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UserService {

    @Resource
    private UsersMapper userMapper;


    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "simple";

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode    星球编号
     * @return 新用户 id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号过长");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        Users user = new Users();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }


    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    @Override
    public Users userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 6) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        // column: 对应是数据库字段名称
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        Users user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 3. 用户脱敏
        Users safetyUser = updateSessionUser(user, request);
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public Users getSafetyUser(Users originUser) {
        if (originUser == null) {
            return null;
        }
        Users safetyUser = new Users();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


    @Override
    public List<Users> searchUsersByTags(Set<String> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<Users> qw = new QueryWrapper<>();
        tags.forEach(tag -> qw.like("tags",  tag));

        return userMapper.selectList(qw);
    }

    @Override
    public List<Users> searchUsersByTagsInMem(Set<String> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<String> newTags = tags.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        List<Users> users = userMapper.selectList(null);
        return Optional.ofNullable(users).orElse(new ArrayList<>())
                .stream()
                .filter(user -> {
                    for (String tag : newTags) {
                        if (!user.getTags().contains(tag)) {
                            return false;
                        }
                    }
                    return !CollectionUtils.isEmpty(newTags);
                    // 空集合 allMatch: 返回也是true
//                    List<String> stringList = tags.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
//                    return stringList.stream().allMatch(tag -> {
//                        boolean contains = user.getTags().contains(tag);
//                        return contains;
//                    });
                })
                .collect(Collectors.toList());
    }

    @Override
    public int updateUser(Users users, HttpServletRequest request) {
        // 1、参数校验
        if (users == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 从当前session中获取用户信息，
        Users loginUser = getCurrentUser(request);

        Long userId = users.getId();
        // 2.判断当前用户是否为admin或者是修改的是自己的信息
        if (!isAdmin(request) && !userId.equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        // 3. 更新用户信息
        Users oldUser = userMapper.selectById(userId);
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        int result = userMapper.updateById(users);
        if (result == 1) {
            // 更新成功
            // 在查询一次
            Users newUser = userMapper.selectById(userId);
            updateSessionUser(newUser, request);
        }



        return result;
    }

    private Users updateSessionUser(Users users, HttpServletRequest request) {
        // 3. 用户脱敏
        Users safetyUser = getSafetyUser(users);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        Users user = (Users) userObj;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public Users getCurrentUser(HttpServletRequest request) {
        Object loginUserObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (loginUserObj == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (Users) loginUserObj;
    }

    @Override
    public Users getLoginUser(HttpServletRequest request) {
        Object loginUserObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        return (Users) loginUserObj;
    }


}

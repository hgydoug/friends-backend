package com.simple.friends.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.friends.contant.CommonConstant;
import com.simple.friends.model.domain.Users;
import com.simple.friends.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: dougHu
 * @date: 2024年06月10日 21:29
 * @description: 缓存预热
 */
@Component
@Slf4j
public class PreCacheJob {


//    @Scheduled(cron = "*/5 * * * * *")  // 每隔5s触发一次
//    public void doCacheRecommendUser() {
//        System.out.println("doCacheRecommendUser");
//    }

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    // 重点用户
    private final List<Long> mainUserList = List.of(2L);


    // 每天执行，预热推荐用户 （每天12:31，写缓存。）
//    @Scheduled(cron = "0 31 0 * * *")
    public void doCacheRecommendUser() {
        // 1、预热多少页的数据，
        // 2、缓存的时间
        // 3. 预热哪些用户的数据

        RLock lock = redissonClient.getLock(CommonConstant.RECOMMEND_USER_CACHE_LOCK);
        try {
            // 只有一个线程能获取到锁
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                log.debug("getLock: " + Thread.currentThread().getId());
                for (Long userId : mainUserList) {
                    // 防止重复更新的操作。
                    String redisKey = String.format("%s:%s", CommonConstant.USER_RECOMMEND_PREFIX ,userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    Object oldObj = valueOperations.get(redisKey);
                    if (oldObj != null) {
                        continue;
                    }

                    try {
                        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
                        Page<Users> userPage = userService.page(new Page<>(1, 20), queryWrapper);

                        // 写缓存, 设置失效时间。
                        valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redis set key error", e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("doCacheRecommendUser error", e);
        } finally {
            // 只能释放自己的锁
            log.debug("unLock: " + Thread.currentThread().getId());
            lock.unlock();

        }
    }


}

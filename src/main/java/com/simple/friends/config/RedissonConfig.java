package com.simple.friends.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", redisProperties.getHost(), redisProperties.getPort());
        config.useSingleServer().setAddress(redisAddress);

        return Redisson.create(config);
    }

}

package com.simple.friends.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis相关配置
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String , Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 这个connectionFactory的Bean是哪里定义的：boot帮忙创建的
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // 默认的Key/Field序列化器为：JdkSerializationRedisSerializer
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(RedisSerializer.string());
//        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        // 使用Jackson2json而非GenericJackson2json：主要为解决LocalDataTime序列化问题
//        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        // 配置localDataTime序列化
//        jsonRedisSerializer.setObjectMapper(getLocalDataTimeMapper());
//        // 配置value序列化
//        redisTemplate.setValueSerializer(jsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jsonRedisSerializer);


        return redisTemplate;
    }

//    private ObjectMapper getLocalDataTimeMapper() {
//        // 这里定义了OM中对于LocalDataTime的序列化规则
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//
//        //LocalDatetime序列化
//        JavaTimeModule timeModule = new JavaTimeModule();
//
//        timeModule.addDeserializer(LocalDate.class,
//                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        timeModule.addSerializer(LocalDate.class,
//                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//
//        timeModule.addDeserializer(LocalDateTime.class,
//                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        timeModule.addSerializer(LocalDateTime.class,
//                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//
//        // 注册localDataTime序列化
//        objectMapper.registerModule(timeModule);
//
//        return objectMapper;
//    }
}

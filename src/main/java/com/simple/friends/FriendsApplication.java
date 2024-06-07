package com.simple.friends;

import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author doughu
 */
@SpringBootApplication
@MapperScan("com.simple.friends.mapper")
public class FriendsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(FriendsApplication.class, args);
        String property = applicationContext.getEnvironment().getProperty("login.exclude.path");
        System.out.println(property);
//        Login bean = applicationContext.getBean(Login.class);
//        System.out.println(bean.path);
    }

//    @Component
//    @ConfigurationProperties(value = "login.exclude")
//    @Data
//    static class Login {
//        private List<String> path;
//    }

}

package com.simple.friends;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author doughu
 */
@SpringBootApplication
@MapperScan("com.simple.friends.mapper")
public class FriendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendsApplication.class, args);
    }

}

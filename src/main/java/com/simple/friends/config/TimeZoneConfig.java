package com.simple.friends.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//@Configuration
public class TimeZoneConfig {

    @PostConstruct
    void started() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println(" 时区 ： "  + TimeZone.getDefault());
    }
}
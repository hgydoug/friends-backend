package com.simple.friends.model.request;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TeamAddRequest implements Serializable {


    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 过期时间 timezone="GMT+8"
     * timezone: 前端传入的字符串，告诉springmvc时间转换器，对方传输过来的时间时区是GMT+8 （东八区），而本地时区也是东八区，
     * springmvc就不会进行转换了。默认情况下springmvc，把传入过来的时间当成UTC时区的时间，而本应用（程序）时区是东八区，
     * springmvc就会将传入的时间+8
     */
    @JsonFormat(pattern = "yyyy/MM/dd", timezone="GMT+8")
    private Date expireTime;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;

}

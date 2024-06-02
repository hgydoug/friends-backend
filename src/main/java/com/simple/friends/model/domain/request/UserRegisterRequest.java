package com.simple.friends.model.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@ApiModel(description = "注册对象")
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", required = true, dataType = "String")
    private String userAccount;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", required = true, dataType = "String")
    private String userPassword;

    /**
     * 校验密码
     */
    @ApiModelProperty(value = "校验密码", required = true, dataType = "String")
    private String checkPassword;

    /**
     * 星球编号
     */
    @ApiModelProperty(value = "星球编号", required = true, dataType = "String")
    private String planetCode;
}
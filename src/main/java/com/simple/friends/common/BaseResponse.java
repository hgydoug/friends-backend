package com.simple.friends.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 */
//@ApiModel(description = "响应参数对象")
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int code;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private T data;

    /**
     * 消息
     */
    @ApiModelProperty(value = "消息")
    private String message;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}

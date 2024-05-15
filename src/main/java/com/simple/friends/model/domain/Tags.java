package com.simple.friends.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName tags
 */
@TableName(value ="tags")
@Data
public class Tags implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 上传标签的用户
     */
    private Long userId;

    /**
     * 分类ID
     */
    private Long parentId;

    /**
     * 是否为父级标签
     */
    private Integer parent;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 0-未删除 1-已删除
     */
    private Integer del;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
create database if not exists friends;

use friends;

-- 用户表
CREATE TABLE `users` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `username` varchar(256) DEFAULT NULL COMMENT '用户昵称',
     `user_account` varchar(256) DEFAULT NULL COMMENT '账号',
     `avatar_url` varchar(1024) DEFAULT NULL COMMENT '用户头像',
     `gender` tinyint DEFAULT NULL COMMENT '性别',
     `user_password` varchar(512) NOT NULL COMMENT '密码',
     `phone` varchar(128) DEFAULT NULL COMMENT '电话',
     `email` varchar(512) DEFAULT NULL COMMENT '邮箱',
     `user_status` int NOT NULL DEFAULT '0' COMMENT '状态 0 - 正常',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     `del` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
     `user_role` int NOT NULL DEFAULT '0' COMMENT '用户角色 0 - 普通用户 1 - 管理员',
     `planet_code` varchar(512) DEFAULT NULL COMMENT '星球编号',
     `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户标签',
     `profile` varchar(256) DEFAULT NULL COMMENT '个人简介',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

-- alter table users add column profile varchar(256) null comment '用户描述信息'

-- 标签表
CREATE TABLE `tags` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `tag_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
    `user_id` bigint NOT NULL COMMENT '上传标签的用户',
    `parent_id` bigint NOT NULL COMMENT '分类ID',
    `parent` tinyint NOT NULL DEFAULT '0' COMMENT '是否为父级标签',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `del` tinyint NOT NULL DEFAULT '0' COMMENT '0-未删除 1-已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签';

-- 队伍表
CREATE TABLE `team` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '队伍名称',
    `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '队伍描述',
    `max_num` int NOT NULL DEFAULT '1' COMMENT '队伍最大人数',
    `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
    `user_id` bigint NOT NULL COMMENT '创建队伍用户ID',
    `status` int NOT NULL DEFAULT '0' COMMENT '0 - 公开，1 - 私有，2 - 加密',
    `password` varchar(512) DEFAULT NULL COMMENT '密码',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `del` tinyint NOT NULL COMMENT '是否删除 0-未删除 1-已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='队伍';


-- 用户队伍关系
-- create table if not exists user_team (
--     id         bigint auto_increment comment 'id' primary key,
--     user_id     bigint comment '用户id',
--     team_id     bigint comment '队伍id',
--     join_time   datetime null comment '加入时间',
--     create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
--     update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
--     del   tinyint  default 0 not null comment '是否删除'
-- ) comment '用户队伍关系';

-- 用户队伍关系
CREATE TABLE `user_team` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `user_id` bigint DEFAULT NULL COMMENT '用户id',
     `team_id` bigint DEFAULT NULL COMMENT '队伍id',
     `join_time` datetime DEFAULT NULL COMMENT '加入时间',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     `del` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户队伍关系';
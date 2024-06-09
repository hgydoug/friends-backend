package com.simple.friends.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.simple.friends.model.domain.Team;
import com.simple.friends.service.TeamService;
import com.simple.friends.mapper.TeamMapper;
import org.springframework.stereotype.Service;

/**
* @author hgy
* @description 针对表【team】的数据库操作Service实现
* @createDate 2024-06-09 21:20:53
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService{

}





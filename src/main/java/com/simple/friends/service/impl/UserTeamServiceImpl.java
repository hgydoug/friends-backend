package com.simple.friends.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.friends.model.domain.UserTeam;
import com.simple.friends.service.UserTeamService;
import com.simple.friends.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author hgy
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-06-09 21:25:40
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




package com.simple.friends.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.friends.model.domain.Team;
import com.simple.friends.model.domain.Users;
import com.simple.friends.model.dto.TeamQuery;
import com.simple.friends.model.request.TeamJoinRequest;
import com.simple.friends.model.request.TeamQuitRequest;
import com.simple.friends.model.request.TeamUpdateRequest;
import com.simple.friends.model.vo.TeamUserVO;

import java.util.List;

/**
* @author hgy
* @description 针对表【team】的数据库操作Service
* @createDate 2024-06-09 21:20:53
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @return
     */
    long addTeam(Team team);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest);

    /**
     * 删除队伍
     * @param id
     * @return
     */
    boolean deleteTeam(long id);

    /**
     * 获取当前用户的创建队伍
     * @param teamQuery
     * @param isAdmin: 是否位管理员
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);
}

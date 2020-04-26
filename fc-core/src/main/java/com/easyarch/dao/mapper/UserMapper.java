package com.easyarch.dao.mapper;


import com.easyarch.model.PlayerInfo;
import com.easyarch.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("select count(*) from UserInfo where userId = #{userId,jdbcType=VARCHAR}")
    int searchById(String id);

    @Select("select count(*) from UserInfo where userId = #{userName,jdbcType=VARCHAR}")
    int searchByName(String name);

    @Select("select * from UserInfo where userId = #{userId,jdbcType=VARCHAR}")
    UserInfo searchUserById(String id);

    @Insert("insert into UserInfo(userId, userPwd) values (#{userId},#{userPwd})")
    int insertUser(UserInfo user);

    @Select("select userId from UserInfo where userId=#{userId} and userPwd = #{userPwd}")
    UserInfo searchUserByUserInfo(UserInfo user);

    @Update("        update PlayerInfo set fightCount = #{fightCount} ,\n" +
            "                              winCount = #{winCount},\n" +
            "                              money = #{money},\n" +
            "                              climbLevel = #{climbLevel},\n" +
            "                              PlayerInfo.`rank` = #{rank} where userId = #{userId}")
    int updatePlayer(PlayerInfo player);

    @Select("select * from PlayerInfo where userId = #{userId,jdbcType=VARCHAR};")
    PlayerInfo getPlayer(String userId);

    @Insert({"    insert into PlayerInfo(userId, userName, fightCount, winCount, money, climbLevel, PlayerInfo.`rank`)\n" +
            "         values (#{userId},#{userName},#{fightCount},#{winCount},#{money},#{climbLevel},#{rank})"})
    int insertPlayer(PlayerInfo player);

}

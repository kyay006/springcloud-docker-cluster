package com.liu.spring.service.log.mapper;


import com.liu.spring.domain.log.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * 微信小程序
 * @author lang.liu
 **/
@Mapper
public interface LogLoginMapper {


    @Insert("INSERT INTO lovesound_log_login(userId, openId,loginType,createTime)VALUES (#{userId},#{openId},#{loginType},#{createTime})")
    void saveLogLogin(LoveSoundLogLogin loveSoundLogLogin);


    @Insert("INSERT INTO lovesound_log_search(userId, userAgeStart,userAgeEnd,userHeightStart,userHeightEnd," +
            "userIndustry,userEducation,userCharacter,userWeightStart,userWeightEnd,userMarriage,gender,createTime)" +
            "VALUES (#{userId},#{userAgeStart},#{userAgeEnd},#{userHeightStart},#{userHeightEnd},#{userIndustry}," +
            "#{userEducation},#{userCharacter},#{userWeightStart},#{userWeightEnd},#{userMarriage},#{gender}," +
            "#{createTime})")
    void saveLogSearch(LoveSoundLogSearch loveSoundLogSearch);

    @Update({ "update lovesound_log_search set userId=23 where id = 3 "  })
    void updateLogSearch();


    @Update({ "update lovesound_log_search set userId=25 where id = 3 "  })
    void updateLogSearch1();


    @Insert({ "INSERT INTO `lovesound_log_userinfo` (`userId`,  `userNickname`, " +
            "`userAvatarurl`, `loveWords`, `email`, `ageFormat`, `userAge`, `userHeight`, `userWeight`, `userEducation`, `userMarriage`, `userIndustry`, `userOccupation`, " +
            "`userCharacter`, `userHobby`, `createTime`)"+
            "VALUES (#{userId},  #{userNickname}," +
            "#{userAvatarurl}, #{loveWords}, #{email}, #{ageFormat}, #{userAge}, #{userHeight}, #{userWeight}, #{userEducation}, #{userMarriage}, #{userIndustry}, #{userOccupation}," +
            "#{userCharacter}, #{userHobby}, #{createTime})" })
    void saveLogUserInfo(LoveSoundLogUserInfo loveSoundLogUserInfo);



    @Insert({ "INSERT INTO `lovesound_log_readuser` (`readUserId`,  `createTime`, `createUserId`, `createUserIp`)",
            "VALUES (#{readUserId},  #{createTime}, #{createUserId}, #{createUserIp})" })
    void saveLogReadUser(LoveSoundLogReadUser loveSoundLogReadUser);

    @Select({ "SELECT * FROM lovesound_log_login WHERE userId = #{userId} ORDER BY createTime DESC LIMIT 1 "})
    LoveSoundLogLogin getLogLoginLast(long userId);

    @Select({ "SELECT * FROM lovesound_template_msg WHERE userId = #{userId} AND createTime >= #{indexDay} ORDER BY createTime DESC LIMIT 1 "})
    LoveSoundTemplateMsg getLoveSoundTemplateMsgLastByUserId(@Param("userId") long userId,@Param("indexDay") Date indexDay);

    @Insert("INSERT INTO lovesound_template_msg(userId, content,createTime,createUserId)VALUES (#{userId},#{content},#{createTime},#{createUserId})")
    void saveLoveSoundTemplateMsg(LoveSoundTemplateMsg loveSoundTemplateMsg);
}
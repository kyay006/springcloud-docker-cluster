package com.liu.spring.service.showpeople.mapper;


import com.liu.spring.domain.smallprogram.WxUser;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * 微信小程序
 * @author lang.liu
 **/
@Mapper
public interface ShowPeopleMapper {

    @Select("SELECT id,userNickname,userAvatarurl,loveWords,gender,createDate FROM wx_user WHERE gender = #{gender} order by createDate desc")
    List<WxUser> getBaseUserInfoList(@Param("gender") int gender);

    @Select({"<script>",
            "SELECT id,userNickname,userAvatarurl,userAge,userHeight,gender,userIndustry,userCharacter,createDate FROM wx_user WHERE gender = #{gender} ",
            "<if test='userAgeStart != null and userAgeStart != \"\"'>",
            "   and userAge &lt;= #{userAgeStart} ",
            "</if>",
            "<if test='userAgeEnd != null and userAgeEnd != \"\"'>",
            "   and userAge &gt;= #{userAgeEnd} ",
            "</if>",
            "<if test='userHeightStart != null and userHeightStart != \"\"'>",
            "   and userHeight &gt;= #{userHeightStart} ",
            "</if>",
            "<if test='userHeightEnd != null and userHeightEnd != \"\"'>",
            "   and userHeight &lt;= #{userHeightEnd} ",
            "</if>",
            "<if test='userIndustry != null and userIndustry != \"\"'>",
            "   and ${userIndustry} ",
            "</if>",
            "<if test='userEducation != null and userEducation != \"\"'>",
            "   and userEducation &gt;= #{userEducation} ",
            "</if>",
            "<if test='userCharacter != null and userCharacter != \"\"'>",
            "   and ${userCharacter} ",
            "</if>",
            "<if test='userWeightStart != null and userWeightStart != \"\"'>",
            "   and userWeight &gt;= #{userWeightStart} ",
            "</if>",
            "<if test='userWeightEnd != null and userWeightEnd != \"\"'>",
            "   and userWeight &lt;= #{userWeightEnd} ",
            "</if>",
            "<if test='userMarriage != null and userMarriage != \"\"'>",
            "   and userMarriage = #{userMarriage} ",
            "</if>",
            "order by createDate desc",
            "</script>"})
    List<WxUser> getMatchUserInfoList(HashMap param);

}
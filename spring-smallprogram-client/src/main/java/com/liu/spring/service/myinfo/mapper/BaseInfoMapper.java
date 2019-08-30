package com.liu.spring.service.myinfo.mapper;


import com.liu.spring.domain.myinfo.LoveSoundUserDynamic;
import com.liu.spring.domain.myinfo.LoveSoundUserSubscribe;
import com.liu.spring.domain.smallprogram.WxUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 微信小程序
 * @author lang.liu
 **/
@Mapper
public interface BaseInfoMapper {


    @Update({"<script>",
            "update wx_user",
            "<set>",
            "<if test='ageFormat != null'>",
            "ageFormat = #{ageFormat} ,",
            "</if>",
            "<if test='userAge != 0'>",
            "userAge = #{userAge} ,",
            "</if>",
            "<if test='userHeight != 0'>",
            "userHeight = #{userHeight} ,",
            "</if>",
            "<if test='userWeight != 0'>",
            "userWeight = #{userWeight} ,",
            "</if>",
            "<if test='userEducation != null'>",
            "userEducation = #{userEducation} ,",
            "</if>",
            "<if test='userMarriage != null'>",
            "userMarriage = #{userMarriage} ,",
            "</if>",
            "<if test='userIndustry != null'>",
            "userIndustry = #{userIndustry} ,",
            "</if>",
            "<if test='userOccupation != null'>",
            "userOccupation = #{userOccupation} ,",
            "</if>",
            "<if test='userCharacter != null'>",
            "userCharacter = #{userCharacter} ,",
            "</if>",
            "<if test='userHobby != null'>",
            "userHobby = #{userHobby} ,",
            "</if>",
            "<if test='userNickname != null'>",
            "userNickname = #{userNickname} ,",
            "</if>",
            "<if test='userAvatarurl != null'>",
            "userAvatarurl = #{userAvatarurl} ,",
            "</if>",
            "<if test='loveWords != null'>",
            "loveWords = #{loveWords} ,",
            "</if>",
            "<if test='email != null'>",
            "email = #{email} ,",
            "</if>",
            "<if test='formIds != null'>",
            "formIds = #{formIds} ,",
            "</if>",
            "updateDate = #{updateDate} ",
            "</set>",
            "where id = #{id}",
            "</script>"})
    void saveBaseInfo(WxUser wxUser);

    @Insert({ "INSERT INTO `lovesound_user_dynamic` (`dynamicContent`, `dynamicImgUrls`, `dynamicImgIds`, `remark`, `createTime`, `createUserId`, `updateTime`, `updateUserId`)"+
            "VALUES (#{dynamicContent}, #{dynamicImgUrls}, #{dynamicImgIds}, #{remark}, #{createTime}, #{createUserId}, #{updateTime}, #{updateUserId})" })
    void saveUserDynamic(LoveSoundUserDynamic loveSoundUserDynamic);

    @Select("SELECT userDy.id,userDy.dynamicContent,userDy.dynamicImgUrls,userDy.dynamicImgIds,userDy.remark,userDy.createTime,userDy.updateTime,wxUser.userNickname,wxUser.userAvatarurl FROM lovesound_user_dynamic userDy " +
            "LEFT JOIN wx_user wxUser on userDy.createUserId = wxUser.id WHERE userDy.createUserId = #{userId} AND userDy.status = #{status}  ORDER BY userDy.createTime DESC")
    List<LoveSoundUserDynamic> getUserDynamicList(@Param("userId") long userId,@Param("status") String status);

    @Select("SELECT userDy.id,userDy.dynamicContent,userDy.dynamicImgUrls,userDy.dynamicImgIds,userDy.remark,userDy.createTime,createUserId,userDy.updateTime FROM lovesound_user_dynamic userDy where userDy.id = #{id}")
    LoveSoundUserDynamic getLoveSoundUserDynamicById(@Param("id")long id);

    @Update({"<script>",
            "update lovesound_user_dynamic",
            "<set>",
            "<if test='status != null'>",
            "status = #{status} ,",
            "</if>",
            "updateUserId = #{updateUserId} ,updateTime = #{updateTime} ",
            "</set>",
            "where id = #{id}",
            "</script>"})
    void updateUserDynamic(LoveSoundUserDynamic loveSoundUserDynamic);

    @Select("SELECT id,passiveUserId,subscribeStatus,createTime,createUserId,updateTime,updateUserId FROM lovesound_user_subscribe WHERE createUserId = #{userId} AND passiveUserId = #{passiveUserId}")
    LoveSoundUserSubscribe getSubscribeServiceByUserId(@Param("userId")String userId,@Param("passiveUserId") long passiveUserId);


    @Insert("INSERT INTO lovesound_user_subscribe(passiveUserId, subscribeStatus,createTime, createUserId,updateTime,updateUserId)VALUES (#{passiveUserId},#{subscribeStatus},#{createTime},#{createUserId},#{updateTime},#{updateUserId})")
    void saveWaterUserSubscribe(LoveSoundUserSubscribe loveSoundUserSubscribe);

    @Update("UPDATE lovesound_user_subscribe  SET passiveUserId = #{passiveUserId},subscribeStatus = #{subscribeStatus},updateUserId = #{updateUserId},updateTime = #{updateTime} WHERE id =#{id}")
    void updateWaterUserSubscribe(LoveSoundUserSubscribe loveSoundUserSubscribe);



    @Select("SELECT ifnull(count(id),0) FROM lovesound_user_subscribe WHERE passiveUserId = #{userId} and subscribeStatus = #{subscribeStatus}")
    int getUserFansCount(@Param("userId")long userId,@Param("subscribeStatus") String subscribeStatus);


    @Select("SELECT ifnull(count(id),0) FROM lovesound_user_subscribe WHERE createUserId = #{userId}  and subscribeStatus = #{subscribeStatus}")
    int getUserSubscribeCount(@Param("userId")long userId,@Param("subscribeStatus") String subscribeStatus);


    @Select("SELECT u_user.id, u_user.userNickname, u_user.userAvatarurl, u_user.loveWords,u_user.createDate FROM lovesound_user_subscribe subscribe INNER JOIN wx_user u_user  ON subscribe.passiveUserId = u_user.id  WHERE subscribe.createUserId = #{pageUserId} and subscribeStatus = #{subscribeStatus} ORDER BY subscribe.createTime DESC")
    List<WxUser> getSubscribeUserList(@Param("pageUserId") long pageUserId,@Param("subscribeStatus") String subscribeStatus);

    @Select("SELECT u_user.id, u_user.userNickname, u_user.userAvatarurl, u_user.loveWords,u_user.createDate FROM lovesound_user_subscribe subscribe INNER JOIN wx_user u_user  ON subscribe.passiveUserId = u_user.id  WHERE subscribe.passiveUserId = #{pageUserId} and subscribeStatus = #{subscribeStatus} ORDER BY subscribe.createTime DESC")
    List<WxUser> getFansUserList(@Param("pageUserId")long pageUserId, @Param("subscribeStatus")String subscribeStatus);

    @Select("SELECT ageFormat,userHeight,userWeight,userEducation,userMarriage,userIndustry,userOccupation,userCharacter,userHobby,email FROM wx_user WHERE id = #{userId}  and status = #{status}")
    WxUser getWxUserBaseInfo(@Param("userId")String userId,@Param("status") String status);

    @Select("SELECT count(id) FROM water_message_board WHERE messageUserId = #{userId} AND readStatus = #{UnRead} AND status = #{status} ")
    int getUnReadBoardByUserId(@Param("userId")Long userId,@Param("UnRead") String UnRead, @Param("status") String status);
}
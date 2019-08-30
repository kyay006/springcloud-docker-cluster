package com.liu.spring.service.authorize.mapper;


import com.liu.spring.domain.smallprogram.WxUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * 微信小程序
 * @author lang.liu
 **/
@Mapper
public interface AuthorizeMapper {

    @Select("SELECT * FROM wx_user WHERE `openid` = #{openid} limit 1")
    WxUser getWxUserByOpenId(String openId);

    @Insert({ "INSERT INTO `wx_user` (`openid`, `nickname`, `avatarurl`, `mobile`, `telnum`, `gender`, `country`, `province`, `city`, `language`, `userNickname`, " +
              "`userAvatarurl`, `loveWords`, `email`, `ageFormat`, `userAge`, `userHeight`, `userWeight`, `userEducation`, `userMarriage`, `userIndustry`, `userOccupation`, " +
              "`userCharacter`, `userHobby`, `createDate`, `updateDate`)"+
              "VALUES (#{openid}, #{nickname}, #{avatarurl}, #{mobile}, #{telnum}, #{gender}, #{country}, #{province}, #{city}, #{language}, #{userNickname}," +
              "#{userAvatarurl}, #{loveWords}, #{email}, #{ageFormat}, #{userAge}, #{userHeight}, #{userWeight}, #{userEducation}, #{userMarriage}, #{userIndustry}, #{userOccupation}," +
              "#{userCharacter}, #{userHobby}, #{createDate}, #{updateDate})" })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveWxUser(WxUser wxUser);

    @Select("SELECT * FROM wx_user WHERE `id` = #{userId}")
    WxUser getWxUserById(long userId);



}
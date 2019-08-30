package com.liu.lovesound.springorder.mapper;


import com.liu.lovesound.springorder.LoveSoundLogLogin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * 微信小程序
 * @author lang.liu
 **/
@Mapper
public interface LogLoginMapper {


    @Insert("INSERT INTO lovesound_log_login(userId, openId,loginType,createTime)VALUES (#{userId},#{openId},#{loginType},#{createTime})")
    void saveLogLogin(LoveSoundLogLogin loveSoundLogLogin);

    @Select("select * from  lovesound_log_search where id=3")
    Map upLogLogin();

    @Update("update lovesound_log_search set userId=24 where id = 9 ")
    void updateLogLogin();


}
package com.liu.spring.service.wechat.mapper;


import com.liu.spring.domain.wechat.LoveSoundWechatUpwall;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * 微信订阅号
 * @author lang.liu
 **/
@Mapper
public interface SubscriptionMapper {


    @Insert({"INSERT INTO lovesound_wechat_upwall(name, sex,wechat,baseInfo,interest,selfIntroduction,demand,img1,img2,img3,img4,img5,imgUrls,",
            "examineStatus,createTime,createUserIp)",
             "VALUES (#{name},#{sex},#{wechat},#{baseInfo},#{interest},#{selfIntroduction},#{demand},#{img1},#{img2},#{img3},#{img4},#{img5},#{imgUrls},",
            "#{examineStatus},#{createTime},#{createUserIp})"})
    void saveUpWallInfo(LoveSoundWechatUpwall loveSoundWechatUpwall);


    @Select({"<script>",
            "SELECT id,name, sex,wechat,baseInfo,interest,selfIntroduction,demand,img1,img2,img3,img4,img5,imgUrls,examineStatus,examineTime,publishTime,status,createTime,createUserIp ",
            "from  lovesound_wechat_upwall  ",
            "<where>",
                "<if test='status != null and status != \"\"' >",
                "   AND status = #{status} ",
                "</if>",
                "<if test='examineStatus != null' >",
                "   AND examineStatus = #{examineStatus} ",
                "</if>",
            "</where>",
            "ORDER BY createTime desc",
            "</script>"})
    List<LoveSoundWechatUpwall> getUpWallInfoList(@Param("examineStatus") Integer examineStatus,@Param("status") String status);

    @Select({"<script>",
            "SELECT id,name, sex,wechat,baseInfo,interest,selfIntroduction,demand,img1,img2,img3,img4,img5,imgUrls,examineStatus,examineTime,publishTime,status,createTime,createUserIp ",
            "from  lovesound_wechat_upwall where id = #{id}",
            "</script>"})
    LoveSoundWechatUpwall getUpWallExamineById(long id);


    @Update({"<script>",
            "update lovesound_wechat_upwall set examineStatus = #{examineStatus} , examineTime = #{examineTime} ",
            "<if test='examineStatus == 3'>",
            ",publishTime = #{examineTime} ",
            "</if>",
            "<if test='status != null'>",
            ",status = #{status} ",
            "</if>",
            " where id = #{id} ",
            "</script>"})
    void updateUpWallExamine(@Param("id") long id, @Param("examineStatus") int examineStatus, @Param("examineTime") Date examineTime,@Param("status") String status);
}
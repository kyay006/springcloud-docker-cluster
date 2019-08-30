package com.liu.spring.service.appointment.mapper;


import com.liu.spring.domain.appointment.LoveSoundAppointment;
import com.liu.spring.domain.appointment.LoveSoundAppointmentSign;
import com.liu.spring.domain.article.LoveSoundArticle;
import com.liu.spring.domain.commoninfo.LoveSoundReport;
import com.liu.spring.domain.smallprogram.WxUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 文章
 * @author lang.liu
 **/
@Mapper
public interface AppointmentMapper {


    @Insert({"INSERT INTO lovesound_appointment(title,wechat,selfIntroduction,formality,`condition`,appointmentTime,address,img1,img2,img3,img4,img5,imgUrls,",
            "hasTop,province,city,status,createTime,createUserId,updateTime,updateUserId)",
             "VALUES (#{title},#{wechat},#{selfIntroduction},#{formality},#{condition},#{appointmentTime},#{address},#{img1},#{img2},#{img3},#{img4},#{img5},#{imgUrls},",
            "#{hasTop},#{province},#{city},#{status},#{createTime},#{createUserId},#{updateTime},#{updateUserId})"})
    void saveAppointment(LoveSoundAppointment loveSoundAppointment);


    @Select({"<script>",
            " SELECT DISTINCT art.id,art.title, art.wechat, art.formality, art.`condition`, art.selfIntroduction, art.appointmentTime,art.address,",
            " art.img1, art.img2, art.img3, art.img4, art.img5, art.imgUrls, art.hasTop, art.topTime, art.status, art.createTime, art.createUserId, art.updateTime, art.updateUserId, ",
            " wxu.userNickname, wxu.userAvatarurl, wxu.gender, wxu.userAge ",
            "<if test='selfUserId != null' >",
            "   ,(SELECT count(wwww.id) FROM lovesound_appointment_sign wwww WHERE wwww.appointmentId = art.id AND wwww.readStatus = 0 AND wwww.status = #{status}) AS weiduCount ",
            "</if>",
            " from  lovesound_appointment art ",
            " left join  wx_user wxu on art.createUserId = wxu.id ",
            " left join  lovesound_appointment_sign lax on lax.appointmentId = art.id ",
            "<where>",
                "<if test='selfUserMessage != null and selfUserMessage' >",
                "   AND lax.createUserId = #{selfUserId} AND lax.status != 'DELETE' AND art.status != 'DELETE' ",
                "</if>",

                "<if test='selfUserMessage == null and status != null and status != \"\" and selfUserId == null' >",
                "   AND art.status = #{status} ",
                "</if>",
                "<if test='selfUserMessage == null and gender != null' >",
                "   AND wxu.gender = #{gender} ",
                "</if>",
                "<if test='selfUserMessage == null and selfUserId != null' >",
                "   AND wxu.id = #{selfUserId} AND art.status != 'DELETE' ",
                "</if>",
            "</where>",
            " ORDER BY art.appointmentTime <![CDATA[ <> ]]> #{indexDay},art.appointmentTime <![CDATA[ <> ]]> #{mDay},art.appointmentTime desc  ",
            "</script>"})
    //今天和明天的排前面
    List<LoveSoundAppointment> getAppointmentList(@Param("status") String status, @Param("gender") Integer gender, @Param("selfUserId") String selfUserId,@Param("selfUserMessage")Boolean selfUserMessage,
                                                  @Param("indexDay") String indexDay, @Param("mDay") String mDay);

    @Select({"<script>",
            "SELECT id,title,wechat,formality,`condition`,selfIntroduction,appointmentTime,address,img1,img2,img3,img4,img5,imgUrls,hasTop,topTime,status,createTime,createUserId,updateTime,updateUserId ",
            "from  lovesound_appointment  ",
            "<where>",
                "<if test='status != null and status != \"\"' >",
                "   AND status = #{status} ",
                "</if>",
                "<if test='userId != null' >",
                "   AND createUserId = #{userId} ",
                "</if>",
            "</where>",
            "ORDER BY updateTime desc",
            "</script>"})
    List<LoveSoundArticle> getAppointmentListByUserId(@Param("status") String status, @Param("userId") String userId);

    @Select({"<script>",
            " SELECT id,title,wechat,formality,`condition`,selfIntroduction,appointmentTime,address,img1,img2,img3,img4,img5,imgUrls,hasTop,topTime,status,createTime,createUserId,updateTime,updateUserId ",
            " from  lovesound_appointment ",
            " where id = #{id} and status != 'DELETE' ",
            "</script>"})
    @Results({
            @Result(property="id",column="id"),
            @Result(property="title",column="title"),
            @Result(property="wechat",column="wechat"),
            @Result(property="formality",column="formality"),
            @Result(property="condition",column="condition"),
            @Result(property="selfIntroduction",column="selfIntroduction"),
            @Result(property="appointmentTime",column="appointmentTime"),
            @Result(property="img1",column="img1"),
            @Result(property="img2",column="img2"),
            @Result(property="img3",column="img3"),
            @Result(property="img4",column="img4"),
            @Result(property="img5",column="img5"),
            @Result(property="imgUrls",column="imgUrls"),
            @Result(property="hasTop",column="hasTop"),
            @Result(property="topTime",column="topTime"),
            @Result(property="status",column="status"),
            @Result(property="createTime",column="createTime"),
            @Result(property="createUserId",column="createUserId"),
            @Result(property="updateTime",column="updateTime"),
            @Result(property="updateUserId",column="updateUserId"),
            @Result(property="wxUser",column="createUserId",
            one = @One(select = "com.liu.spring.service.appointment.mapper.AppointmentMapper.getUserById"))
    })
    LoveSoundAppointment getAppointmentById(long id);

    @Select({"<script>",
            " SELECT wxu.userNickname, wxu.userAvatarurl, wxu.gender, wxu.userAge, wxu.loveWords, wxu.userHeight, wxu.userWeight, wxu.userEducation, ",
            " wxu.userMarriage, wxu.userIndustry, wxu.userOccupation, wxu.userCharacter, wxu.userHobby ",
            " from  wx_user wxu",
            " where id = #{id} and status != 'DELETE' ",
            "</script>"})
    WxUser getUserById(long id);

    @Update({"<script>",
            "update lovesound_appointment set updateTime = #{updateTime} , updateUserId = #{updateUserId} ",
            "<if test='title != null and title != \"\"'>",
            ",title = #{title} ",
            "</if>",
            "<if test='wechat != null and wechat != \"\"'>",
            ",wechat = #{wechat} ",
            "</if>",
            "<if test='formality != null and formality != \"\"'>",
            ",formality = #{formality} ",
            "</if>",
            "<if test='condition != null and condition != \"\"'>",
            ",`condition` = #{condition} ",
            "</if>",
            "<if test='selfIntroduction != null and selfIntroduction != \"\"'>",
            ",selfIntroduction = #{selfIntroduction} ",
            "</if>",
            "<if test='address != null and address != \"\"'>",
            ",address = #{address} ",
            "</if>",
            "<if test='img1 != null and img1 != 0'>",
            ",img1 = #{img1} ",
            "</if>",
            "<if test='img2 != null and img2 != 0'>",
            ",img2 = #{img2} ",
            "</if>",
            "<if test='img3 != null and img3 != 0'>",
            ",img3 = #{img3} ",
            "</if>",
            "<if test='img4 != null'>",
            ",img4 = #{img4} ",
            "</if>",
            "<if test='img5 != null'>",
            ",img5 = #{img5} ",
            "</if>",
            "<if test='imgUrls != null and imgUrls != \"\"'>",
            ",imgUrls = #{imgUrls} ",
            "</if>",
            "<if test='appointmentTime != null'>",
            ",appointmentTime = #{appointmentTime} ",
            "</if>",
            "<if test='hasTop != null'>",
            ",hasTop = #{hasTop} ",
            "</if>",
            "<if test='topTime != null'>",
            ",topTime = #{topTime} ",
            "</if>",
            "<if test='province != null and province != \"\"'>",
            ",province = #{province} ",
            "</if>",
            "<if test='city != null and city != \"\"'>",
            ",city = #{city} ",
            "</if>",
            "<if test='status != null and status != \"\"'>",
            ",status = #{status} ",
            "</if>",
            " where id = #{id} and status != 'DELETE' and createUserId = #{createUserId}",
            "</script>"})
    void updateAppointment(LoveSoundAppointment loveSoundAppointment);


    /**
     * 查询以前是否举报过
     * @param articleId
     * @param userId
     * @param functionId
     * @return
     */
    @Select({"<script>",
            "SELECT id,reportReason,functionId,dataId,reportStatus,status,reportUserId,createTime,",
            " createUserId,updateTime,updateUserId ",
            " FROM lovesound_report ",
            " WHERE functionId = #{functionId} AND dataId = #{articleId} ",
            " AND createUserId = #{createUserId} ",
            "</script>"})
    LoveSoundReport getReportByArticleId(@Param("articleId") long articleId, @Param("createUserId") String userId, @Param("functionId") String functionId);

    /**
     * 获取当天之内此用户在此功能上总共举报的次数
     * @param funName
     * @param userId
     * @param day
     * @return
     */
    @Select({"<script>",
            " SELECT count(id) FROM lovesound_report ",
            " WHERE functionId = #{functionId} AND createUserId = #{createUserId} AND createTime > #{day} ",
            "</script>"})
    Integer getdayReportCountByUserId(@Param("functionId") String funName, @Param("createUserId") String userId, @Param("day") String day);

    /**
     * 举报文章
     * @param waterReport
     */
    @Insert({"INSERT INTO lovesound_report (reportReason, functionId,dataId,reportUserId, createTime, createUserId,updateTime,updateUserId) ",
            "VALUES (#{reportReason},#{functionId},#{dataId},#{reportUserId},#{createTime},#{createUserId},#{updateTime},#{updateUserId}) " })
    void reportArticle(LoveSoundReport waterReport);



    /**
     * 查询以前是否报名过
     * @param appointmentId
     * @param userId
     * @return
     */
    @Select({"<script>",
            "SELECT id,appointmentId, messageUserId, content, status, createTime,createUserId ",
            " FROM lovesound_appointment_sign ",
            " WHERE appointmentId = #{appointmentId} AND createUserId = #{createUserId} AND status != 'DELETE' ",
            "</script>"})
    LoveSoundAppointmentSign getMessageId(@Param("appointmentId") long appointmentId, @Param("createUserId") String userId);

    /**
     * 报名描述 保存
     * @param loveSoundAppointmentSign
     */
    @Insert({"INSERT INTO lovesound_appointment_sign (appointmentId, messageUserId, content,readStatus, status, createTime,createUserId) ",
            " VALUES (#{appointmentId},#{messageUserId}, #{content},#{readStatus},#{status}, #{createTime}, #{createUserId} ) " })
    void saveMessage(LoveSoundAppointmentSign loveSoundAppointmentSign);

    /**
     * 删除一条留言及其回复
     * @param id
     */
    @Update({" UPDATE lovesound_appointment_sign SET status = 'DELETE'  WHERE (id = #{id}) AND createUserId = #{userId} " })
    void delMessage(@Param("id") String id, @Param("userId") String userId);

    /**
     * 读取某个文章的留言列表
     * @return
     */
    @Select({"<script>",
            " SELECT message.id,message.messageUserId,message.appointmentId,message.content,message.status,message.createTime,message.createUserId,  ",
            " wx_user.userNickname as createUserName,wx_user.userAvatarurl as headImgUriPath  ",
            " FROM lovesound_appointment_sign message  ",
            " INNER JOIN wx_user ON message.createUserId = wx_user.id  ",
            " WHERE message.messageUserId = #{userId} AND message.appointmentId = #{appointmentId}",
            " <if test=\"status != null\">  ",
            "    AND message.status = #{status}  ",
            " </if>  ",
            " ORDER BY message.createTime DESC",
            "</script>"})
    @Results({@Result(property="id",column="id"),
              @Result(property="messageUserId",column="messageUserId"),
              @Result(property="appointmentId",column="appointmentId"),
              @Result(property="content",column="content"),
              @Result(property="createTime",column="createTime"),
              @Result(property="createUserId",column="createUserId"),
              @Result(property="createUserName",column="createUserName"),
              @Result(property="headImgUriPath",column="headImgUriPath")
    })
    List<LoveSoundAppointmentSign> messageList(@Param("userId") Long userId, @Param("appointmentId") Long appointmentId, @Param("status") String status);


    /**
     * 将此文章的未读留言设置为已读
     * @param id
     * @param HaveRead
     */
    @Update({" UPDATE lovesound_appointment_sign SET readStatus = #{HaveRead} WHERE appointmentId = #{id} " })
    void updateReadStatus(@Param("id")long id,@Param("HaveRead") String HaveRead);

    /**
     * 获取文章未读总数量
     * @param userId
     * @param status
     * @return
     */
    @Select({"<script>",
            " SELECT count(wwww.id) FROM lovesound_appointment_sign wwww ",
            " INNER JOIN lovesound_appointment aaa ON wwww.appointmentId = aaa.id ",
            " WHERE wwww.messageUserId = #{userId} AND wwww.readStatus = 0 AND wwww.status = #{status} AND aaa.status = #{status} ",
            "</script>"})
    Integer getArticleWeiDuCount(@Param("userId")String userId,@Param("status") String status);
}
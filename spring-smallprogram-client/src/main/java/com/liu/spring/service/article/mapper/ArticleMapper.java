package com.liu.spring.service.article.mapper;


import com.liu.spring.domain.article.LoveSoundArticle;
import com.liu.spring.domain.article.WaterArticleRead;
import com.liu.spring.domain.article.WaterArticleZan;
import com.liu.spring.domain.article.WaterMessageBoard;
import com.liu.spring.domain.commoninfo.LoveSoundReport;
import com.liu.spring.domain.smallprogram.WxUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 文章
 * @author lang.liu
 **/
@Mapper
public interface ArticleMapper {


    @Insert({"INSERT INTO lovesound_article(title,wechat,baseInfo,interest,selfIntroduction,demand,img1,img2,img3,img4,img5,imgUrls,",
            "hasTop,province,city,status,createTime,createUserId,updateTime,updateUserId)",
             "VALUES (#{title},#{wechat},#{baseInfo},#{interest},#{selfIntroduction},#{demand},#{img1},#{img2},#{img3},#{img4},#{img5},#{imgUrls},",
            "#{hasTop},#{province},#{city},#{status},#{createTime},#{createUserId},#{updateTime},#{updateUserId})"})
    void saveArticle(LoveSoundArticle loveSoundArticle);


    @Select({"<script>",
            " SELECT art.id,art.title, art.wechat, art.baseInfo, art.interest, art.selfIntroduction, art.demand,",
            " art.img1, art.img2, art.img3, art.img4, art.img5, art.imgUrls, art.hasTop, art.topTime, art.status, art.createTime, art.createUserId, art.updateTime, art.updateUserId, ",
            " wxu.userNickname, wxu.userAvatarurl, wxu.gender, wxu.userAge ",
            "<if test='selfUserId != null' >",
            "   ,(SELECT count(wwww.id) FROM water_message_board wwww WHERE wwww.articleId = art.id AND wwww.readStatus = 0 AND wwww.status = #{status}) AS weiduCount ",
            "</if>",
            " from  lovesound_article art ",
            " left join  wx_user wxu on art.createUserId = wxu.id ",
            "<where>",
                "<if test='status != null and status != \"\"' >",
                "   AND art.status = #{status} ",
                "</if>",
                "<if test='gender != null' >",
                "   AND wxu.gender = #{gender} ",
                "</if>",
                "<if test='selfUserId != null' >",
                "   AND wxu.id = #{selfUserId} ",
                "</if>",
            "</where>",
            " ORDER BY art.updateTime desc",
            "</script>"})
    List<LoveSoundArticle> getArticleList(@Param("status") String status,@Param("gender") Integer gender,@Param("selfUserId") String selfUserId);

    @Select({"<script>",
            " SELECT art.id,art.title, art.wechat, art.baseInfo, art.interest, art.selfIntroduction, art.demand,",
            " art.img1, art.img2, art.img3, art.img4, art.img5, art.imgUrls, art.hasTop, art.topTime, art.status, art.createTime, art.createUserId, art.updateTime, art.updateUserId, ",
            " wxu.userNickname, wxu.userAvatarurl, wxu.gender, wxu.userAge ",
            "from  lovesound_article  art ",
            " left join  wx_user wxu on art.createUserId = wxu.id ",
            "<where>",
                "<if test='status != null and status != \"\"' >",
                "   AND art.status = #{status} ",
                "</if>",
                "<if test='userId != null' >",
                "   AND art.createUserId = #{userId} ",
                "</if>",
            "</where>",
            "ORDER BY art.updateTime desc",
            "</script>"})
    List<LoveSoundArticle> getArticleListByUserId(@Param("status") String status,@Param("userId") String userId);

    @Select({"<script>",
            " SELECT id,title,wechat,baseInfo,interest,selfIntroduction,demand,img1,img2,img3,img4,img5,imgUrls,hasTop,topTime,status,createTime,createUserId,updateTime,updateUserId ",
            " from  lovesound_article ",
            " where id = #{id} and status != 'DELETE' ",
            "</script>"})
    @Results({
            @Result(property="id",column="id"),
            @Result(property="title",column="title"),
            @Result(property="wechat",column="wechat"),
            @Result(property="baseInfo",column="baseInfo"),
            @Result(property="interest",column="interest"),
            @Result(property="selfIntroduction",column="selfIntroduction"),
            @Result(property="demand",column="demand"),
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
            one = @One(select = "com.liu.spring.service.article.mapper.ArticleMapper.getUserById"))
    })
    LoveSoundArticle getArticleById(long id);

    @Select({"<script>",
            " SELECT wxu.userNickname, wxu.userAvatarurl, wxu.gender, wxu.userAge, wxu.loveWords, wxu.userHeight, wxu.userWeight, wxu.userEducation, ",
            " wxu.userMarriage, wxu.userIndustry, wxu.userOccupation, wxu.userCharacter, wxu.userHobby, wxu.email ",
            " from  wx_user wxu",
            " where id = #{id} and status != 'DELETE' ",
            "</script>"})
    WxUser getUserById(long id);

    @Update({"<script>",
            "update lovesound_article set updateTime = #{updateTime} , updateUserId = #{updateUserId} ",
            "<if test='title != null and title != \"\"'>",
            ",title = #{title} ",
            "</if>",
            "<if test='wechat != null and wechat != \"\"'>",
            ",wechat = #{wechat} ",
            "</if>",
            "<if test='baseInfo != null and baseInfo != \"\"'>",
            ",baseInfo = #{baseInfo} ",
            "</if>",
            "<if test='interest != null and interest != \"\"'>",
            ",interest = #{interest} ",
            "</if>",
            "<if test='selfIntroduction != null and selfIntroduction != \"\"'>",
            ",selfIntroduction = #{selfIntroduction} ",
            "</if>",
            "<if test='demand != null and demand != \"\"'>",
            ",demand = #{demand} ",
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
    void updateArticle(LoveSoundArticle loveSoundArticle);


    /**
     * 判断当前用户对这篇文章是否点过赞或倒赞
     */
    @Select({"<script>",
            "SELECT id,articleId,zanStatus,createTime,createUserId FROM water_article_zan WHERE articleId = #{articleId} AND createUserId = #{userId}",
            "</script>"})
    WaterArticleZan getArticleZanByArticleId(@Param("articleId")long articleId,@Param("userId") String userId);

    /**
     * 修改文章 赞或倒赞
     * @param waterArticleZan
     */
    @Update({"<script>",
            "UPDATE water_article_zan  SET zanStatus = #{zanStatus} WHERE id = #{id}",
            "</script>"})
    void updateOperationArticle(WaterArticleZan waterArticleZan);

    /**
     * 保存文章 赞或倒赞
     * @param waterArticleZan
     */
    @Insert({"INSERT INTO water_article_zan (articleId, zanStatus,createTime,createUserId) ",
            " VALUES (#{articleId}, #{zanStatus}, #{createTime}, #{createUserId}  )"})
    void saveOperationArticle(WaterArticleZan waterArticleZan);

    /**
     * 保存 文章阅读数据
     * @param waterArticleRead
     */
    @Insert({" insert into water_article_read (articleId,articlecreateUserId,createTime,createUserId,createUserIp) ",
            "  VALUES (#{articleId},#{articlecreateUserId},#{createTime},#{createUserId},#{createUserIp}  )"})
    void saveArticleReadFlow(WaterArticleRead waterArticleRead);


    /**
     * 获取此文章的阅读数
     * @param articleId
     * @return
     */
    @Select({"<script>",
            "SELECT count(id) FROM water_article_read articleRead WHERE articleRead.articleId = #{articleId}",
            "</script>"})
    int getArticleReadFlowCount(@Param("articleId")long articleId);


    /**
     * 获取此文章的赞数
     * @param articleId
     * @return
     */
    @Select({"<script>",
            "SELECT count(id) FROM water_article_zan articleZan WHERE articleZan.articleId = #{articleId}",
            "</script>"})
    int getArticleZanCount(long articleId);

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
    LoveSoundReport getReportByArticleId(@Param("articleId")long articleId,@Param("createUserId") String userId,@Param("functionId") String functionId);

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
    Integer getdayReportCountByUserId(@Param("functionId")String funName, @Param("createUserId")String userId, @Param("day")String day);

    /**
     * 举报文章
     * @param waterReport
     */
    @Insert({"INSERT INTO lovesound_report (reportReason, functionId,dataId,reportUserId, createTime, createUserId,updateTime,updateUserId) ",
            "VALUES (#{reportReason},#{functionId},#{dataId},#{reportUserId},#{createTime},#{createUserId},#{updateTime},#{updateUserId}) " })
    void reportArticle(LoveSoundReport waterReport);

    /**
     * 删除文章
     * @param id
     * @param userId
     */
    @Delete({" DELETE FROM lovesound_article WHERE id = #{id} AND status =#{status} AND createUserId = #{userId} " })
    void delArticle(@Param("id")String id,@Param("userId") String userId);

    /**
     * 查询当前最高楼层
     * @param articleId
     * @return
     */
    @Select({"<script>",
            " SELECT count(id) FROM water_message_board  WHERE articleId = #{articleId} AND status = #{status} AND replyMessageId IS NULL ",
            "</script>"})
    Integer getMaxFloorByUserId(@Param("articleId")Long articleId,@Param("status") String status);

    /**
     * 留言或回复 保存
     * @param waterMessageBoard
     */
    @Insert({"INSERT INTO water_message_board (articleId, messageUserId, content,floor,replyMessageId, status, createTime,createUserId) ",
            " VALUES (#{articleId},#{messageUserId}, #{content}, #{floor}, #{replyMessageId}, #{status}, #{createTime}, #{createUserId} ) " })
    void saveMessage(WaterMessageBoard waterMessageBoard);

    /**
     * 删除一条留言及其回复
     * @param id
     */
//    @Delete({" DELETE FROM water_message_board WHERE id = #{id} OR replyMessageId =#{id} " })
    @Update({" UPDATE water_message_board SET status = 'DELETE'  WHERE (id = #{id} OR replyMessageId =#{id}) AND createUserId = #{userId} " })
    void delMessage(@Param("id")String id,@Param("userId") String userId);

    /**
     * 读取某个文章的留言列表
     * @return
     */
    @Select({"<script>",
            " SELECT message.id,message.messageUserId,message.articleId,message.content,message.floor, message.replyMessageId,message.status,message.createTime,message.createUserId,  ",
            " wx_user.userNickname as createUserName,wx_user.userAvatarurl as headImgUriPath  ",
            " FROM water_message_board message  ",
            " INNER JOIN wx_user ON message.createUserId = wx_user.id  ",
            " WHERE message.messageUserId = #{userId} AND message.replyMessageId IS NULL AND message.articleId = #{articleId}",
            " <if test=\"status != null\">  ",
            "    AND message.status = #{status}  ",
            " </if>  ",
            " ORDER BY message.floor DESC",
            "</script>"})
    @Results({@Result(property="id",column="id"),
              @Result(property="articleId",column="articleId"),
              @Result(property="messageUserId",column="messageUserId"),
              @Result(property="content",column="content"),
              @Result(property="floor",column="floor"),
              @Result(property="replyMessageId",column="replyMessageId"),
              @Result(property="createTime",column="createTime"),
              @Result(property="createUserId",column="createUserId"),
              @Result(property="createUserName",column="createUserName"),
              @Result(property="headImgUriPath",column="headImgUriPath"),
              @Result(property="waterMessageReplyList",column="id",
              many = @Many(select = "com.liu.spring.service.article.mapper.ArticleMapper.getMessageReply"))
    })
    List<WaterMessageBoard> messageList(@Param("userId")Long userId,@Param("articleId") Long articleId,@Param("status") String status);

    @Select({"SELECT board.id,board.messageUserId,board.content,board.floor, ",
            "        board.replyMessageId,board.status,board.createTime,board.createUserId, ",
            "        wx_user.userNickname as createUserName,wx_user.userAvatarurl as headImgUriPath ",
            "        FROM water_message_board board ",
            "        INNER JOIN wx_user ON board.createUserId = wx_user.id ",
            "        WHERE board.replyMessageId=#{id} AND board.status = 'ACTIVE' ",
            "        ORDER BY board.createTime ASC"})
    List<WaterMessageBoard> getMessageReply(@Param("id")int id);


    /**
     * 将此文章的未读留言设置为已读
     * @param id
     * @param HaveRead
     */
    @Update({" UPDATE water_message_board SET readStatus = #{HaveRead} WHERE articleId = #{id} " })
    void updateReadStatus(@Param("id")long id,@Param("HaveRead") String HaveRead);

    /**
     * 获取文章未读总数量
     * @param userId
     * @param status
     * @return
     */
    @Select({"<script>",
            " SELECT count(wwww.id) FROM water_message_board wwww ",
            " INNER JOIN lovesound_article aaa ON wwww.articleId = aaa.id ",
            " WHERE wwww.messageUserId = #{userId} AND wwww.readStatus = 0 AND wwww.status = #{status} AND aaa.status = #{status} ",
            "</script>"})
    Integer getArticleWeiDuCount(@Param("userId")String userId,@Param("status") String status);
}
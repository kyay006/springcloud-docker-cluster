package com.liu.spring.service.chat.mapper;


import com.liu.spring.domain.chat.LoveSoundChatList;
import com.liu.spring.domain.chat.LoveSoundChatMsg;
import com.liu.spring.domain.smallprogram.WxUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 微信小程序
 * @author lang.liu
 **/
@Mapper
public interface ChatMapper {


    List<WxUser> getBaseUserInfoList(@Param("gender") int gender);

    //获取聊天列表
    @Select({"SELECT lcl.id,lcl.sendUserId,lcl.receiverUserId,lcl.sendUnReadCount,lcl.receiverUnReadCount,lcl.lastContent,lcl.lastSendDate,lcl.createTime,",
            "wxuReceiver.userNickname userNicknameReceiver,wxuReceiver.userAvatarurl userAvatarurlReceiver, ",
            "wxuSend.userNickname userNicknameSend,wxuSend.userAvatarurl userAvatarurlSend ",
            "FROM lovesound_chat_list lcl INNER JOIN wx_user wxuReceiver ON lcl.receiverUserId = wxuReceiver.id ",
            "INNER JOIN wx_user wxuSend ON lcl.sendUserId = wxuSend.id ",
            "WHERE (lcl.sendUserId = #{userId} OR lcl.receiverUserId = #{userId}) AND lcl.status = #{status} ",
            "ORDER BY lcl.updateTime DESC"})
    List<LoveSoundChatList> getUserChatList(@Param("userId")String userId, @Param("status") String status);


    //获取与此用户的聊天的最后30条信息

    @Select({"SELECT lcl.id,lcl.sendUserId,lcl.receiverUserId,lcl.content,lcl.image,lcl.sendDate,lcl.haveRead,lcl.msgType,lcl.createTime,",
            "wxuReceiver.userNickname userNicknameReceiver,wxuReceiver.userAvatarurl userAvatarurlReceiver, ",
            "wxuSend.userNickname userNicknameSend,wxuSend.userAvatarurl userAvatarurlSend ",
            "FROM lovesound_chat_msg lcl INNER JOIN wx_user wxuReceiver ON lcl.receiverUserId = wxuReceiver.id ",
            "INNER JOIN wx_user wxuSend ON lcl.sendUserId = wxuSend.id ",
            "WHERE ((lcl.sendUserId = #{userId} AND lcl.receiverUserId = #{userIdLogin}) OR (lcl.sendUserId = #{userIdLogin} AND lcl.receiverUserId = #{userId})) ",
            "AND lcl.status = #{status} ",
            "ORDER BY lcl.createTime DESC"})
    List<LoveSoundChatMsg> getUserChatMsgLastList(@Param("userId")long userId, @Param("userIdLogin")String userIdLogin,@Param("status") String status);


    @Insert({ "INSERT INTO `lovesound_chat_msg` (`sendUserId`, `receiverUserId`, `content`, `imageId`, `image`, `sendDate`, `status`, `haveRead`, `msgType`, `createTime`, `createUserId`, " +
            "`updateTime`, `updateUserId`)"+
            "VALUES (#{sendUserId}, #{receiverUserId}, #{content}, #{imageId}, #{image}, #{sendDate}, #{status}, #{haveRead}, #{msgType}, #{createTime}, #{createUserId}," +
            "#{updateTime}, #{updateUserId})" })
    void saveChatMsg(LoveSoundChatMsg loveSoundChatMsg);


    @Select({"SELECT lcl.id,lcl.sendUserId,lcl.receiverUserId,lcl.sendUnReadCount,lcl.receiverUnReadCount,lcl.lastContent,lcl.lastSendDate,lcl.status,lcl.createTime",
            "FROM lovesound_chat_list lcl ",
            "WHERE ((lcl.sendUserId = #{sendUserId} AND lcl.receiverUserId = #{receiverUserId}) OR (lcl.sendUserId = #{receiverUserId} AND lcl.receiverUserId = #{sendUserId})) ",
            "LIMIT 1 "})
    LoveSoundChatList getLoveSoundChatListByTowUserId(@Param("sendUserId")long sendUserId, @Param("receiverUserId")long receiverUserId);

    @Insert({ "INSERT INTO `lovesound_chat_list` (`sendUserId`, `receiverUserId`, `sendUnReadCount`,`receiverUnReadCount`, `lastContent`, `lastSendDate`, `status`,  `createTime`, `createUserId`, " +
            "`updateTime`, `updateUserId`)"+
            "VALUES (#{sendUserId}, #{receiverUserId}, #{sendUnReadCount},#{receiverUnReadCount}, #{lastContent}, #{lastSendDate}, #{status},  #{createTime}, #{createUserId}," +
            "#{updateTime}, #{updateUserId})" })
    void saveLoveSoundChatList(LoveSoundChatList loveSoundChatList);

    @Update({"<script>",
            "UPDATE lovesound_chat_list  ",
            "<set>",
            "<if test='lastContent != null'>",
            "lastContent = #{lastContent},",
            "</if>",
            "<if test='lastSendDate != null'>",
            "lastSendDate = #{lastSendDate},",
            "</if>",
            "<if test='sendUnReadCount != null'>",
            "sendUnReadCount = #{sendUnReadCount},",
            "</if>",
            "<if test='receiverUnReadCount != null'>",
            "receiverUnReadCount = #{receiverUnReadCount},",
            "</if>",
            "status = #{status},updateTime = #{updateTime},updateUserId = #{updateUserId} ",
            "</set>",
            " WHERE id =#{id}",
            "</script>"})
    void updateLoveSoundChatList(LoveSoundChatList loveSoundChatList);
}
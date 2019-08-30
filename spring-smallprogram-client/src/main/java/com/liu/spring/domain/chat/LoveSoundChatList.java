package com.liu.spring.domain.chat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by lang.liu on 2019/2/5.
 *聊天列表表 lovesound_chat_list
 */
public class LoveSoundChatList
{

    private Long id;
    private long sendUserId;/*信息发送人id*/
    private long receiverUserId;/*信息接收人id*/
    private Integer sendUnReadCount;/*消息未读数量-发送人用*/
    private Integer receiverUnReadCount;/*消息未读数量-接收人用*/
    private String lastContent;/*最后一条发送的内容,如果是图片的话,这里就保存图片这两个字*/
    private Timestamp lastSendDate;/*最后一条发送的时间*/
    private String status;/*状态,ACTIVE为正常活动状态,DELETE为删除状态*/
    private Timestamp createTime;
    private Long createUserId;
    private Timestamp updateTime;
    private Long updateUserId;


    //关联查出来的用户信息
    private String userNicknameSend;
    private String userAvatarurlSend;
    private String userNicknameReceiver;
    private String userAvatarurlReceiver;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public long getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(long receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public Integer getSendUnReadCount() {
        return sendUnReadCount;
    }

    public void setSendUnReadCount(Integer sendUnReadCount) {
        this.sendUnReadCount = sendUnReadCount;
    }

    public Integer getReceiverUnReadCount() {
        return receiverUnReadCount;
    }

    public void setReceiverUnReadCount(Integer receiverUnReadCount) {
        this.receiverUnReadCount = receiverUnReadCount;
    }

    public String getLastContent() {
        return lastContent;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Timestamp getLastSendDate() {
        return lastSendDate;
    }

    public void setLastSendDate(Timestamp lastSendDate) {
        this.lastSendDate = lastSendDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }


    public String getUserNicknameSend() {
        return userNicknameSend;
    }

    public void setUserNicknameSend(String userNicknameSend) {
        this.userNicknameSend = userNicknameSend;
    }

    public String getUserAvatarurlSend() {
        return userAvatarurlSend;
    }

    public void setUserAvatarurlSend(String userAvatarurlSend) {
        this.userAvatarurlSend = userAvatarurlSend;
    }

    public String getUserNicknameReceiver() {
        return userNicknameReceiver;
    }

    public void setUserNicknameReceiver(String userNicknameReceiver) {
        this.userNicknameReceiver = userNicknameReceiver;
    }

    public String getUserAvatarurlReceiver() {
        return userAvatarurlReceiver;
    }

    public void setUserAvatarurlReceiver(String userAvatarurlReceiver) {
        this.userAvatarurlReceiver = userAvatarurlReceiver;
    }
}
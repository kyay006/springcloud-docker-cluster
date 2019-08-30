package com.liu.spring.domain.chat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by lang.liu on 2019/2/5.
 *聊天消息表 lovesound_chat_msg
 */
public class LoveSoundChatMsg
{
    private Long id;
    private long sendUserId;/*信息发送人id*/
    private long receiverUserId;/*信息接收人id*/
    private String content;/*消息内容*/
    private long imageId;/*消息图片id*/
    private String image;/*消息图片uri*/
    private Timestamp sendDate;/*发送时间*/
    private String status;/*状态,ACTIVE为正常活动状态,DELETE为删除状态*/
    private boolean haveRead;/*是否已读,0表示未读,1表示已读*/
    private int msgType;/*信息类型,0表示文字,1表示图片*/
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isHaveRead() {
        return haveRead;
    }

    public void setHaveRead(boolean haveRead) {
        this.haveRead = haveRead;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
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


    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
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
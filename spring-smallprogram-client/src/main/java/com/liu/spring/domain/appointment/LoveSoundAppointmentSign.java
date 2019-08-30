package com.liu.spring.domain.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by lang.liu on 2017/1/22.15:07
 * 约见报名表 lovesound_appointment_sign
 */
public class LoveSoundAppointmentSign
{
    private Long id;
    private Long messageUserId;/*给这个用户报的名,也就是约见的创建者*/
    private Long appointmentId;/*关联的约见表id*/
    private String content;/*报名的描述*/
    private Integer readStatus;/*该留言是否被用户已读，0表示未读，1表示已读*/
    private String status;/*数据状态,ACTIVE,DELETE,DISABLED,REPORT(举报状态,只有这个用户能举报留言的人)*/
    private Timestamp createTime;
    private Long createUserId;

    private String createUserName;/*这条留言创建人的昵称*/
    private String headImgUriPath;/*这条留言创建人的头像*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(Long messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getHeadImgUriPath() {
        return headImgUriPath;
    }

    public void setHeadImgUriPath(String headImgUriPath) {
        this.headImgUriPath = headImgUriPath;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
package com.liu.spring.domain.myinfo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by lang.liu on 2017/11/21
 * 用户关注其他用户表
 * lovesound_user_subscribe
 */

public class LoveSoundUserSubscribe {

    private Long id;
    private Long passiveUserId;/*被关注的用户id*/
    private int subscribeStatus;/*关注状态，0表示取消关注，1表示关注*/
    private Timestamp createTime;
    private Long createUserId;
    private Timestamp updateTime;
    private Long updateUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassiveUserId() {
        return passiveUserId;
    }

    public void setPassiveUserId(Long passiveUserId) {
        this.passiveUserId = passiveUserId;
    }

    public int getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(int subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
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
}

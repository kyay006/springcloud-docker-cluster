package com.liu.spring.domain.commoninfo;

import java.sql.Timestamp;

/**
 * Created by lang.liu on 2018/1/31.
 *小黑屋表 用来放违规的用户
 */
public class LoveSoundDarkRoom
{
    private Integer id;
    private Long darkUserId;/*关联的用户表id*/
    private String darkCause;/*关进小黑屋的原因*/
    private Status userStatus;/*0为正在被关，1为已释放。多个原因被关进小黑屋有多条记录*/
    private Timestamp createTime;
    private Long createUserId;
    private Timestamp updateTime;
    private Long updateUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDarkUserId() {
        return darkUserId;
    }

    public void setDarkUserId(Long darkUserId) {
        this.darkUserId = darkUserId;
    }

    public String getDarkCause() {
        return darkCause;
    }

    public void setDarkCause(String darkCause) {
        this.darkCause = darkCause;
    }

    public Status getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Status userStatus) {
        this.userStatus = userStatus;
    }

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
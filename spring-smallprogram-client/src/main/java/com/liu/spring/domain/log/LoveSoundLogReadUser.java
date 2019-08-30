package com.liu.spring.domain.log;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lang.liu on 2019/02/6.
 * 用户阅读（查看）其他用户日志表 lovesound_log_readuser
 */
public class LoveSoundLogReadUser implements Serializable {

    private long id;
    private long readUserId;/*被查看的用户id*/
    private Timestamp createTime;/*创建时间*/
    private long createUserId;/*创建人id*/
    private String createUserIp;/*访问用户ip地址,因为用户可以不登录就观看,所以这里存个地址*/


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReadUserId() {
        return readUserId;
    }

    public void setReadUserId(long readUserId) {
        this.readUserId = readUserId;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserIp() {
        return createUserIp;
    }

    public void setCreateUserIp(String createUserIp) {
        this.createUserIp = createUserIp;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
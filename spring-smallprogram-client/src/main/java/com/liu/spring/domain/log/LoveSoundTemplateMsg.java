package com.liu.spring.domain.log;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lang.liu on 2019/02/6.
 * 推送模板消息表 lovesound_template_msg
 */
public class LoveSoundTemplateMsg implements Serializable {

    private long id;
    private long userId;/*推送给了谁 id*/
    private String content;/*内容*/
    private Timestamp createTime;/*创建时间*/
    private long createUserId;/*创建人id,谁推送的*/


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
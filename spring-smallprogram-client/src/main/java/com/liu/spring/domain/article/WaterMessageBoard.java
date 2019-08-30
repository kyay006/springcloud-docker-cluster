package com.liu.spring.domain.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liu.spring.domain.commoninfo.Status;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by lang.liu on 2017/1/22.15:07
 * 学鸣谷用户留言板表
 */
@JsonIgnoreProperties(value = {"handler"})/*因为这里的对象ajax分页查的时候关联了第二条sql，就会有handler代理属性产生，这里设置不让json解析handler代理属性，不然报错*/
public class WaterMessageBoard
{
    private Long id;
    private Long articleId;/*关联的文章表id*/
    private Long messageUserId;/*给这个用户留的言*/
    private String content;/*留言的内容*/
    private Integer floor;/*留言所在的楼层，如果是回复的话楼层数和该留言在同一楼层*/
    private Long replyMessageId;/*如果是回复的话就有值，对应回复的那条留言*/
    private Integer readStatus;/*该留言是否被用户已读，0表示未读，1表示已读*/
    private String status;/*数据状态,ACTIVE,DELETE,DISABLED,REPORT(举报状态,只有这个用户能举报留言的人)*/
    private Timestamp createTime;
    private Long createUserId;

    /*这条留言关联的回复集合*/
    private List<WaterMessageBoard> waterMessageReplyList;
    private String createUserName;/*这条留言创建人的昵称*/
    private String headImgUriPath;/*这条留言创建人的头像*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(Long messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Long getReplyMessageId() {
        return replyMessageId;
    }

    public void setReplyMessageId(Long replyMessageId) {
        this.replyMessageId = replyMessageId;
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

    public List<WaterMessageBoard> getWaterMessageReplyList() {
        return waterMessageReplyList;
    }

    public void setWaterMessageReplyList(List<WaterMessageBoard> waterMessageReplyList) {
        this.waterMessageReplyList = waterMessageReplyList;
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
        if(readStatus == null)
            return Integer.parseInt(Status.UnRead.getValue());
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }
}
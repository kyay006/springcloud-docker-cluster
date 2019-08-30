package com.liu.spring.domain.article;

import java.sql.Timestamp;

/**
 * Created by lang.liu on 2017/1/22.15:03
 * 用户文章点赞表
 */
public class WaterArticleZan
{
    private Long id;
    private Long articleId;/*关联的文章表id*/
    private Integer zanStatus;/*回答的点赞状态，0为倒赞，1为点赞.(一个用户对于一篇文章只能倒赞或只能点赞)*/
    private Timestamp createTime;
    private Long createUserId;

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

    public Integer getZanStatus() {
        return zanStatus;
    }

    public void setZanStatus(Integer zanStatus) {
        this.zanStatus = zanStatus;
    }
}
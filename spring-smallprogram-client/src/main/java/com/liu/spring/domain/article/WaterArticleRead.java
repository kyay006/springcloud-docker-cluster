package com.liu.spring.domain.article;

import java.util.Date;

/**
 * Created by lang.liu on 2017/1/22.15:05
 * 学鸣谷用户文章阅读表  water_article_read
 */
public class WaterArticleRead 
{
    private Long id;
    private Long articleId;/*关联的文章表id*/
    private Long articlecreateUserId;/*关联文章创建者id*/
    private Date createTime;/*创建时间(即阅读时间)*/
    private Long createUserId;
    private String createUserIp;/*访问用户ip地址,因为用户可以不登录就观看,所以这里存个地址*/

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getArticlecreateUserId() {
        return articlecreateUserId;
    }

    public void setArticlecreateUserId(Long articlecreateUserId) {
        this.articlecreateUserId = articlecreateUserId;
    }

    public String getCreateUserIp() {
        return createUserIp;
    }

    public void setCreateUserIp(String createUserIp) {
        this.createUserIp = createUserIp;
    }
}
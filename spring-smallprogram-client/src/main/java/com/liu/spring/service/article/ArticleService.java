package com.liu.spring.service.article;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.spring.domain.article.LoveSoundArticle;
import com.liu.spring.domain.article.WaterArticleRead;
import com.liu.spring.domain.article.WaterArticleZan;
import com.liu.spring.domain.article.WaterMessageBoard;
import com.liu.spring.domain.commoninfo.LoveSoundReport;
import com.liu.spring.domain.commoninfo.Status;
import com.liu.spring.service.article.mapper.ArticleMapper;
import com.liu.util.date.DateUtils;
import com.liu.util.mysql.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iWave on 2019/01/21.
 * 文章
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;


    public void saveArticle(LoveSoundArticle loveSoundArticle) {
        articleMapper.saveArticle(loveSoundArticle);
    }

    public PageInfo getArticleList(PageBean pageBean, String status, Integer gender, String selfUserId) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<LoveSoundArticle> list = articleMapper.getArticleList(status, gender, selfUserId);
        return new PageInfo<>(list);
    }

    public PageInfo getArticleListByUserId(PageBean pageBean, String status, String userId) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<LoveSoundArticle> list = articleMapper.getArticleListByUserId(status, userId);
        return new PageInfo<>(list);
    }

    public LoveSoundArticle getArticleById(long id) {
        return articleMapper.getArticleById(id);
    }

    public void updateArticle(LoveSoundArticle loveSoundArticle) {
        articleMapper.updateArticle(loveSoundArticle);
    }

    public WaterArticleZan getArticleZanByArticleId(long articleId, String userId) {
        return articleMapper.getArticleZanByArticleId(articleId, userId);
    }

    public void updateOperationArticle(WaterArticleZan waterArticleZan) {
        articleMapper.updateOperationArticle(waterArticleZan);
    }

    public void saveOperationArticle(WaterArticleZan waterArticleZan) {
        articleMapper.saveOperationArticle(waterArticleZan);
    }

    public void saveArticleReadFlow(WaterArticleRead waterArticleRead) {
        articleMapper.saveArticleReadFlow(waterArticleRead);
    }

    public int getArticleReadFlowCount(long articleId) {
        return articleMapper.getArticleReadFlowCount(articleId);
    }

    public LoveSoundReport getReportByArticleId(long articleId, String userId) {
        return articleMapper.getReportByArticleId(articleId, userId ,Status.ArticleReport.getValue());
    }

    public Integer getdayReportCountByUserId(String funName, String userId) {
        return articleMapper.getdayReportCountByUserId(funName, userId, DateUtils.getShorDateformat());
    }

    public void reportArticle(LoveSoundReport waterReport) {
        articleMapper.reportArticle(waterReport);
    }

    public void delArticle(String id, String userId) {
        articleMapper.delArticle(id, userId);
    }

    public Integer getMaxFloorByUserId(Long articleId) {
        return articleMapper.getMaxFloorByUserId(articleId, Status.ACTIVE.getValue());
    }

    public void saveMessage(WaterMessageBoard waterMessageBoard) {
        articleMapper.saveMessage(waterMessageBoard);
    }

    public void delMessage(String id, String userId) {
        articleMapper.delMessage(id, userId);
    }

    public List<WaterMessageBoard> messageList(Long messageUserId, Long articleId) {
        return articleMapper.messageList(messageUserId, articleId, Status.ACTIVE.getValue());
    }

    public void updateReadStatus(long id, String status) {
        articleMapper.updateReadStatus(id, status);
    }

    public int getArticleZanCount(long articleId) {
        return articleMapper.getArticleZanCount(articleId);
    }

    public Integer getArticleWeiDuCount(String userId, String status) {
        return articleMapper.getArticleWeiDuCount(userId, status);
    }
}

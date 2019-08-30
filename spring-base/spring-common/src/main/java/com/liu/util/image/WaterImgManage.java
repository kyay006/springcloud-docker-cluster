package com.liu.util.image;

import java.sql.Timestamp;

/**
 * Created by iWave on 2017/2/1.
 * 图片管理表
 */
public class WaterImgManage
{
    private Long id;
    private String absolutePath;
    private String urlPath;
    private String imgFileName;
    private char imgType ;
    private String status;/*数据状态,ACTIVE,DELETE,DISABLED*/
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

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public char getImgType() {
        return imgType;
    }

    public void setImgType(char imgType) {
        this.imgType = imgType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
}

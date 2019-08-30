package com.liu.spring.domain.myinfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lang.liu on 2019/02/3.
 * 用户动态信息表 lovesound_user_dynamic
 */
public class LoveSoundUserDynamic implements Serializable {

    private Long id;
    private String dynamicContent;/*动态内容*/
    private String dynamicImgUrls;/*图片url拼接*/
    private String dynamicImgIds;/*图片表的id,多个用逗号拼接*/
    private String remark;/*备注*/
    private Integer orderNumber;/*排序号*/
    private String status;/*数据状态,ACTIVE,DELETE,DISABLED*/
    private Timestamp createTime;/*创建时间*/
    private Long  createUserId;/*创建人id*/
    private Timestamp updateTime;/*修改时间*/
    private Long  updateUserId;/*修改人id*/


    //关联查出来的用户信息
    private String userNickname;
    private String userAvatarurl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDynamicContent() {
        return dynamicContent;
    }

    public void setDynamicContent(String dynamicContent) {
        this.dynamicContent = dynamicContent;
    }

    public String getDynamicImgUrls() {
        return dynamicImgUrls;
    }

    public void setDynamicImgUrls(String dynamicImgUrls) {
        this.dynamicImgUrls = dynamicImgUrls;
    }

    public String getDynamicImgIds() {
        return dynamicImgIds;
    }

    public void setDynamicImgIds(String dynamicImgIds) {
        this.dynamicImgIds = dynamicImgIds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }


    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserAvatarurl() {
        return userAvatarurl;
    }

    public void setUserAvatarurl(String userAvatarurl) {
        this.userAvatarurl = userAvatarurl;
    }
}
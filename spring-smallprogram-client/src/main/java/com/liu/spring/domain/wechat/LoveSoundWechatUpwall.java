package com.liu.spring.domain.wechat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lang.liu on 2019/03/16.
 * 微信订阅号上墙表 lovesound_wechat_upwall
 */
public class LoveSoundWechatUpwall implements Serializable {

    private long id;
    private String name;/*姓名,网名*/
    private int sex;/*性别,1表示男,0表示女*/
    private String wechat;/*微信号*/
    private String baseInfo;/*年龄 身高 星座 职业 混迹区*/
    private String interest;/*兴趣爱好*/
    private String selfIntroduction;/*自我介绍*/
    private String demand;/*交友要求*/
    private long img1;/*第一张图片,img表的id*/
    private long img2;/*第二张图片,img表的id*/
    private long img3;/*第三张图片,img表的id*/
    private Long img4;/*第四张图片,可为空,img表的id*/
    private Long img5;/*第五张图片,可为空,img表的id*/
    private String imgUrls;/*所有图片的url,多个用逗号拼接*/
    private int examineStatus;/*审核状态,0表示未审核,1表示审核失败,2表示审核成功但未发布,3表示已发布*/
    private Timestamp examineTime;/*审核时间*/
    private Timestamp publishTime;/*发布时间*/
    private String status;/*数据状态,ACTIVE,DELETE*/
    private Timestamp createTime;/*创建时间*/
    private String createUserIp;/*访问用户ip地址,因为用户可以不登录,所以这里存个地址*/








    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public long getImg1() {
        return img1;
    }

    public void setImg1(long img1) {
        this.img1 = img1;
    }

    public long getImg2() {
        return img2;
    }

    public void setImg2(long img2) {
        this.img2 = img2;
    }

    public long getImg3() {
        return img3;
    }

    public void setImg3(long img3) {
        this.img3 = img3;
    }

    public Long getImg4() {
        return img4;
    }

    public void setImg4(Long img4) {
        this.img4 = img4;
    }

    public Long getImg5() {
        return img5;
    }

    public void setImg5(Long img5) {
        this.img5 = img5;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(int examineStatus) {
        this.examineStatus = examineStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Timestamp examineTime) {
        this.examineTime = examineTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
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
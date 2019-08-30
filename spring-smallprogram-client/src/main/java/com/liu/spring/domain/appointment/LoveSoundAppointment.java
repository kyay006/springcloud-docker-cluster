package com.liu.spring.domain.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liu.spring.domain.smallprogram.WxUser;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lang.liu on 2019/03/6.
 * 约见表 lovesound_appointment
 */
public class LoveSoundAppointment implements Serializable {

    private long id;
    private String title;/*标题*/
    private String wechat;/*微信号*/
    private String selfIntroduction;/*自我介绍*/
    private String formality;/*约见形式描述,如散步,吃饭等*/
    private String condition;/*约见条件,对方条件*/
    private Date appointmentTime;/*约见时间*/
    private String address;/*约见地址*/
    private long img1;/*第一张图片,img表的id*/
    private Long img2;/*第二张图片,img表的id*/
    private Long img3;/*第三张图片,img表的id*/
    private Long img4;/*第四张图片,可为空,img表的id*/
    private Long img5;/*第五张图片,可为空,img表的id*/
    private String imgUrls;/*所有图片的url,多个用逗号拼接*/
    private Boolean hasTop;/*默认不置顶,是否置顶，0表示不置顶,1表示置顶*/
    private Timestamp topTime;/*置顶时间*/
    private String province;/*省份*/
    private String city;/*城市*/
    private String status;/*状态,ACTIVE为正常活动状态,DISABLED为禁用或者关闭状态,DELETE为删除状态*/
    private Timestamp createTime;/*创建时间*/
    private String createUserId;/*创建人id*/
    private Timestamp updateTime;/*修改时间*/
    private String updateUserId;/*修改人id*/


    /*数据库关联字段*/
    private String userNickname;
    private String userAvatarurl;
    private String gender;
    private WxUser wxUser;
    private Integer weiduCount;//未读数量

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getFormality() {
        return formality;
    }

    public void setFormality(String formality) {
        this.formality = formality;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public long getImg1() {
        return img1;
    }

    public void setImg1(long img1) {
        this.img1 = img1;
    }

    public Long getImg2() {
        return img2;
    }

    public void setImg2(Long img2) {
        this.img2 = img2;
    }

    public Long getImg3() {
        return img3;
    }

    public void setImg3(Long img3) {
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

    public Boolean getHasTop() {
        return hasTop;
    }

    public void setHasTop(Boolean hasTop) {
        this.hasTop = hasTop;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getTopTime() {
        return topTime;
    }

    public void setTopTime(Timestamp topTime) {
        this.topTime = topTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public WxUser getWxUser() {
        return wxUser;
    }

    public void setWxUser(WxUser wxUser) {
        this.wxUser = wxUser;
    }

    public Integer getWeiduCount() {
        return weiduCount;
    }

    public void setWeiduCount(Integer weiduCount) {
        this.weiduCount = weiduCount;
    }
}
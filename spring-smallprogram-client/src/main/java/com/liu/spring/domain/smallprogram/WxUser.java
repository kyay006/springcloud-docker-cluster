package com.liu.spring.domain.smallprogram;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信用户实体
 * wx_user 微信用户表
 */
public class WxUser implements Serializable {

    private Long id;
    /**
     * 小程序用户openid 28位
     */
    private String openid;
    /**
     * 用户昵称,微信的
     */
    private String nickname;
    /**
     * 用户头像,微信的 512位
     */
    private String avatarurl;
    /**
     * 手机类型,微信的
     */
    private String mobile;
    /**
     * 手机号码,微信的
     */
    private String telnum;

    /**
     * 性别 0：未知、1：男、2：女
     */
    private int gender;
    /**
     * 所在国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 语言
     */
    private String language;
    /**
     * 状态
     */
    private String status;


    /**
     * 用户昵称 100位
     */
    private String userNickname;
    /**
     * 用户头像,自己上传的 512位
     */
    private String userAvatarurl;
    /**
     * 当前情话 100位
     */
    private String loveWords;
    /**
     * 邮箱 64位
     */
    private String email;
    /**
     * 年龄,年月日
     */
    private Date ageFormat;
    /**
     * 年龄,数量
     */
    private int userAge;
    /**
     * 身高,170cm
     */
    private int userHeight;
    /**
     * 体重,50kg
     */
    private int userWeight;
    /**
     * 学历,0表示高中,1表示专科,2表示本科,3表示硕士,4表示博士
     */
    private Integer userEducation;
    /**
     * 婚否,0表示未婚,1表示离异
     */
    private Integer userMarriage;
    /**
     * 行业id,可选择多个行业,如果有多个就用,号拼接
     */
    private String userIndustry;
    /**
     * 职业,自行输入 128位
     */
    private String userOccupation;
    /**
     * 性格id,可选择多个性格,如果有多个就用,号拼接
     */
    private String userCharacter;
    /**
     * 爱好,自行输入 256位
     */
    private String userHobby;
    /**
     * 模板消息用的id串,多个用逗号拼接
     */
    private String formIds;


    /**
     * 数据入库时间
     */
    private Date createDate;
    /**
     * 数据修改时间
     */
    private Date updateDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLoveWords() {
        return loveWords;
    }

    public void setLoveWords(String loveWords) {
        this.loveWords = loveWords;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAgeFormat() {
        return ageFormat;
    }

    public void setAgeFormat(Date ageFormat) {
        this.ageFormat = ageFormat;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public Integer getUserEducation() {
        return userEducation;
    }

    public void setUserEducation(Integer userEducation) {
        this.userEducation = userEducation;
    }

    public Integer getUserMarriage() {
        return userMarriage;
    }

    public void setUserMarriage(Integer userMarriage) {
        this.userMarriage = userMarriage;
    }

    public String getUserIndustry() {
        return userIndustry;
    }

    public void setUserIndustry(String userIndustry) {
        this.userIndustry = userIndustry;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserCharacter() {
        return userCharacter;
    }

    public void setUserCharacter(String userCharacter) {
        this.userCharacter = userCharacter;
    }

    public String getUserHobby() {
        return userHobby;
    }

    public void setUserHobby(String userHobby) {
        this.userHobby = userHobby;
    }

    public String getFormIds() {
        return formIds;
    }

    public void setFormIds(String formIds) {
        this.formIds = formIds;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

package com.liu.spring.domain.log;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lang.liu on 2019/02/6.
 * 用户基本信息历史日志表 lovesound_log_userinfo
 */
public class LoveSoundLogUserInfo implements Serializable {

    private long id;
    private long userId;/*登录人id*/

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

    private Timestamp createTime;/*创建时间*/


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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
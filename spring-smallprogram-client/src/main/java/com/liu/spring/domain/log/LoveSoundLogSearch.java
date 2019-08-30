package com.liu.spring.domain.log;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lang.liu on 2019/02/6.
 * 搜索用户日志表 lovesound_log_search
 */
public class LoveSoundLogSearch implements Serializable {

    private long id;
    private long userId;/*登录人id*/
    private Integer userAgeStart;/*年龄start*/
    private Integer userAgeEnd;/*年龄end*/
    private Integer userHeightStart;/*身高start*/
    private Integer userHeightEnd;/*身高end*/
    private String userIndustry;/*行业*/
    private Integer userEducation;/*最低学历*/
    private String userCharacter;/*兴趣*/
    private Integer userWeightStart;/*体重start*/
    private Integer userWeightEnd;/*体重end*/
    private Integer userMarriage;/*婚否*/
    private Integer gender;/*性别*/
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

    public Integer getUserAgeStart() {
        return userAgeStart;
    }

    public void setUserAgeStart(Integer userAgeStart) {
        this.userAgeStart = userAgeStart;
    }

    public Integer getUserAgeEnd() {
        return userAgeEnd;
    }

    public void setUserAgeEnd(Integer userAgeEnd) {
        this.userAgeEnd = userAgeEnd;
    }

    public Integer getUserHeightStart() {
        return userHeightStart;
    }

    public void setUserHeightStart(Integer userHeightStart) {
        this.userHeightStart = userHeightStart;
    }

    public Integer getUserHeightEnd() {
        return userHeightEnd;
    }

    public void setUserHeightEnd(Integer userHeightEnd) {
        this.userHeightEnd = userHeightEnd;
    }

    public String getUserIndustry() {
        return userIndustry;
    }

    public void setUserIndustry(String userIndustry) {
        this.userIndustry = userIndustry;
    }

    public Integer getUserEducation() {
        return userEducation;
    }

    public void setUserEducation(Integer userEducation) {
        this.userEducation = userEducation;
    }

    public String getUserCharacter() {
        return userCharacter;
    }

    public void setUserCharacter(String userCharacter) {
        this.userCharacter = userCharacter;
    }

    public Integer getUserWeightStart() {
        return userWeightStart;
    }

    public void setUserWeightStart(Integer userWeightStart) {
        this.userWeightStart = userWeightStart;
    }

    public Integer getUserWeightEnd() {
        return userWeightEnd;
    }

    public void setUserWeightEnd(Integer userWeightEnd) {
        this.userWeightEnd = userWeightEnd;
    }

    public Integer getUserMarriage() {
        return userMarriage;
    }

    public void setUserMarriage(Integer userMarriage) {
        this.userMarriage = userMarriage;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
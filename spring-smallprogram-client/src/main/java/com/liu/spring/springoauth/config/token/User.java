package com.liu.spring.springoauth.config.token;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class User implements java.io.Serializable{

    /*用户ID*/
    private int userId;

    /*部门ID*/
    private Integer deptId;

    /*登录账号*/
    private String loginName;

    /*用户昵称*/
    private String userName;

    /*用户类型（00系统用户）*/
    private String userType;

    /*用户邮箱*/
    private String email;

    /*手机号码*/
    private String phoneNumber;

    /*用户性别（0男 1女 2未知）*/
    private Integer sex;

    /*头像路径*/
    private String avatar;

    /*密码*/
    @JsonIgnore
    private String userPassword;

    /*盐加密*/
    private String salt;

    /*帐号状态（0正常 1停用）*/
    private boolean status;

    /*删除标志（0代表存在 2代表删除）*/
    private int delFlag;

    /*最后登陆IP*/
    private String loginIp;

    /*最后登陆时间*/
    private Date loginDate;

    /*创建者*/
    private String createBy;

    /*创建时间*/
    private Date createTime;

    /*更新者*/
    private String updateBy;

    /*更新时间*/
    private Date updateTime;

    /*备注*/
    private String remark;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package com.liu.spring.domain.commoninfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lang.liu on 2016/12/29.
 * 职业表 lovesound_occupation
 */
public class LoveSoundOccupation implements Serializable {

    private Integer id;
    private String  occupationName;/*职业名称*/
    private Integer industryId;/*关联的行业id，职业属于行业*/
    private String remark;/*备注*/
    private Integer orderNumber;/*排序号*/
    private String status;/*数据状态,ACTIVE,DELETE,DISABLED*/
    private Timestamp createTime;/*创建时间*/
    private Long  createUserId;/*创建人id*/
    private Timestamp updateTime;/*修改时间*/
    private Long  updateUserId;/*修改人id*/



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOccupationName() {
        return occupationName;
    }

    public void setOccupationName(String occupationName) {
        this.occupationName = occupationName;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
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

}
package com.liu.spring.domain.commoninfo;

import java.sql.Timestamp;

/**
 * Created by lang.liu on 2017/2/2.
 *用户举报表
 */
public class LoveSoundReport
{
    private Long id;
    private String reportReason; /*举报原因*/
    private Integer functionId; /*举报功能id*/
    private Long dataId; /*举报功能的具体数据id*/
    private Integer reportStatus; /*默认为0,举报是否有效，0为未处理，1为无效，2为有效*/
    private String status; /*数据状态,ACTIVE,DELETE,DISABLED*/
    private Long reportUserId; /*被举报人*/
    private Timestamp createTime; /*举报时间*/
    private Long createUserId; /*举报人*/
    private Timestamp updateTime; /*修改时间*/
    private Long updateUserId;  /*修改人id*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Long reportUserId) {
        this.reportUserId = reportUserId;
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

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
}
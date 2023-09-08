package com.rocsea.etlcommon.model.bo.kettlepdi;

import java.util.Date;

/**
 * @Author RocSea
 * @Date 2022/12/9
 */
public class KettlePackBO {
    private String id;
    private String categoryId;
    private String jobName;
    private String jobDescription;
    private String jobType;
    private String jobPath;
    private String jobRepositoryId;
    private String jobQuartz;
    private String syncStrategy;
    private String jobLogLevel;
    private Integer jobStatus;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private Integer delFlag;
    private String jobParams;
    private Integer monitorSuccess;
    private Integer monitorFail;
    private Date lastExecuteTime;
    private Date nextExecuteTime;
    private Integer errorAlarmFlag;
    private String errorAlarmMails;
    private String runConfig;
    private String remoteServer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobPath() {
        return jobPath;
    }

    public void setJobPath(String jobPath) {
        this.jobPath = jobPath;
    }

    public String getJobRepositoryId() {
        return jobRepositoryId;
    }

    public void setJobRepositoryId(String jobRepositoryId) {
        this.jobRepositoryId = jobRepositoryId;
    }

    public String getJobQuartz() {
        return jobQuartz;
    }

    public void setJobQuartz(String jobQuartz) {
        this.jobQuartz = jobQuartz;
    }

    public String getSyncStrategy() {
        return syncStrategy;
    }

    public void setSyncStrategy(String syncStrategy) {
        this.syncStrategy = syncStrategy;
    }

    public String getJobLogLevel() {
        return jobLogLevel;
    }

    public void setJobLogLevel(String jobLogLevel) {
        this.jobLogLevel = jobLogLevel;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getJobParams() {
        return jobParams;
    }

    public void setJobParams(String jobParams) {
        this.jobParams = jobParams;
    }

    public Integer getMonitorSuccess() {
        return monitorSuccess;
    }

    public void setMonitorSuccess(Integer monitorSuccess) {
        this.monitorSuccess = monitorSuccess;
    }

    public Integer getMonitorFail() {
        return monitorFail;
    }

    public void setMonitorFail(Integer monitorFail) {
        this.monitorFail = monitorFail;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public Integer getErrorAlarmFlag() {
        return errorAlarmFlag;
    }

    public void setErrorAlarmFlag(Integer errorAlarmFlag) {
        this.errorAlarmFlag = errorAlarmFlag;
    }

    public String getErrorAlarmMails() {
        return errorAlarmMails;
    }

    public void setErrorAlarmMails(String errorAlarmMails) {
        this.errorAlarmMails = errorAlarmMails;
    }

    public String getRunConfig() {
        return runConfig;
    }

    public void setRunConfig(String runConfig) {
        this.runConfig = runConfig;
    }

    public String getRemoteServer() {
        return remoteServer;
    }

    public void setRemoteServer(String remoteServer) {
        this.remoteServer = remoteServer;
    }
}

package com.rocsea.etlcommon.model.bo.kettlepdi;

import java.util.Date;

/**
 * @Author RocSea
 * @Date 2023/1/6
 */
public class KettleTaskConfigBO {
    private Long id;
    private Long nextInsertId;
    private Long nextUpdateId;
    private Date nextStartTime;
    private Date nextEndTime;
    private Integer syncStatus;
    private String taskName;
    private Integer taskType;
    private Integer compensationType;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNextInsertId() {
        return nextInsertId;
    }

    public void setNextInsertId(Long nextInsertId) {
        this.nextInsertId = nextInsertId;
    }

    public Long getNextUpdateId() {
        return nextUpdateId;
    }

    public void setNextUpdateId(Long nextUpdateId) {
        this.nextUpdateId = nextUpdateId;
    }

    public Date getNextStartTime() {
        return nextStartTime;
    }

    public void setNextStartTime(Date nextStartTime) {
        this.nextStartTime = nextStartTime;
    }

    public Date getNextEndTime() {
        return nextEndTime;
    }

    public void setNextEndTime(Date nextEndTime) {
        this.nextEndTime = nextEndTime;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(Integer compensationType) {
        this.compensationType = compensationType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

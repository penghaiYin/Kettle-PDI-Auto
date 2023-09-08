package com.rocsea.etlcommon.model.bo.kettlepdi;

/**
 * @Author RocSea
 * @Date 2023/1/31
 */
public class KpJobBO {
    private String jobParam;
    private String jobCompensationParam;
    private String taskName;
    private String jobName;
    private String tableName;
    private String sourceTableName;
    private String targetTableName;

    public String getJobParam() {
        return jobParam;
    }

    public void setJobParam(String jobParam) {
        this.jobParam = jobParam;
    }

    public String getJobCompensationParam() {
        return jobCompensationParam;
    }

    public void setJobCompensationParam(String jobCompensationParam) {
        this.jobCompensationParam = jobCompensationParam;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }
}

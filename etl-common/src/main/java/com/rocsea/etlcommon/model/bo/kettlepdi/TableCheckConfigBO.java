package com.rocsea.etlcommon.model.bo.kettlepdi;

/**
 * @Author RocSea
 * @Date 2023/4/13
 */
public class TableCheckConfigBO {
    private String sourceTableName;
    private Integer sourceDeleteType;
    private String sourceWhereCondition;
    private String targetTableName;
    private String targetWhereCondition;

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public Integer getSourceDeleteType() {
        return sourceDeleteType;
    }

    public void setSourceDeleteType(Integer sourceDeleteType) {
        this.sourceDeleteType = sourceDeleteType;
    }

    public String getSourceWhereCondition() {
        return sourceWhereCondition;
    }

    public void setSourceWhereCondition(String sourceWhereCondition) {
        this.sourceWhereCondition = sourceWhereCondition;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getTargetWhereCondition() {
        return targetWhereCondition;
    }

    public void setTargetWhereCondition(String targetWhereCondition) {
        this.targetWhereCondition = targetWhereCondition;
    }
}

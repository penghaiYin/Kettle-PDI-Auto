package com.rocsea.etlcommon.model.entity.etlsync;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * etl 同步检查配置表
 *
 * @author rocsea
 **/
@Table(name = "etl_sync_check_config")
public class EtlSyncCheckConfigDO {

    /**
     * 主键
     */
    @Id
    @Column
    private Long id;

    /**
     * 业务模块 1:国内重构 2:国内 3:国际 4:新三方 5:卖出库
     */
    @Column
    private Integer sourceServiceModule;
    /**
     * 源数据库名
     */
    @Column
    private String sourceSchemaName;

    /**
     * 源数据库名
     */
    @Column
    private String sourceTableName;

    /**
     * 关联关系
     */
    @Column
    private String sourceJoinCondition;

    /**
     * 删除类型 1:逻辑删除 2:物理删除
     */
    @Column
    private Integer sourceDeleteType;

    /**
     * 查询条件
     */
    @Column
    private String sourceWhereCondition;

    /**
     * 源表类型 1:单表 2:多表
     */
    @Column
    private Integer sourceTableType;

    /**
     * 目标业务模块
     */
    @Column
    private Integer targetServiceModule;

    /**
     * 目标数据库
     */
    @Column
    private String targetSchemaName;

    /**
     * 目标表名
     */
    @Column
    private String targetTableName;

    /**
     * 目标关联条件
     */
    @Column
    private String targetJoinCondition;

    /**
     * 目标删除类型 1逻辑删除 2物理删除
     */
    @Column
    private Integer targetDeleteType;

    /**
     * 目标查询条件
     */
    @Column
    private String targetWhereCondition;

    /**
     * 目标表类型
     */
    @Column
    private Integer targetTableType;

    /**
     * 创建时间
     */
    @Column
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceServiceModule() {
        return sourceServiceModule;
    }

    public void setSourceServiceModule(Integer sourceServiceModule) {
        this.sourceServiceModule = sourceServiceModule;
    }

    public String getSourceSchemaName() {
        return sourceSchemaName;
    }

    public void setSourceSchemaName(String sourceSchemaName) {
        this.sourceSchemaName = sourceSchemaName;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceJoinCondition() {
        return sourceJoinCondition;
    }

    public void setSourceJoinCondition(String sourceJoinCondition) {
        this.sourceJoinCondition = sourceJoinCondition;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public Integer getSourceDeleteType() {
        return sourceDeleteType;
    }

    public void setSourceDeleteType(Integer sourceDeleteType) {
        this.sourceDeleteType = sourceDeleteType;
    }

    public Integer getSourceTableType() {
        return sourceTableType;
    }

    public void setSourceTableType(Integer sourceTableType) {
        this.sourceTableType = sourceTableType;
    }

    public String getSourceWhereCondition() {
        return sourceWhereCondition;
    }

    public void setSourceWhereCondition(String sourceWhereCondition) {
        this.sourceWhereCondition = sourceWhereCondition;
    }

    public Timestamp getCreateTime() {
        return Objects.isNull(createTime) ? null : new Timestamp(createTime.getTime());
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = Objects.isNull(createTime) ? null : new Timestamp(createTime.getTime());
    }

    public Timestamp getUpdateTime() {
        return Objects.isNull(updateTime) ? null : new Timestamp(updateTime.getTime());
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = Objects.isNull(updateTime) ? null : new Timestamp(updateTime.getTime());
    }


    public Integer getTargetServiceModule() {
        return targetServiceModule;
    }

    public void setTargetServiceModule(Integer targetServiceModule) {
        this.targetServiceModule = targetServiceModule;
    }

    public String getTargetSchemaName() {
        return targetSchemaName;
    }

    public void setTargetSchemaName(String targetSchemaName) {
        this.targetSchemaName = targetSchemaName;
    }

    public String getTargetJoinCondition() {
        return targetJoinCondition;
    }

    public void setTargetJoinCondition(String targetJoinCondition) {
        this.targetJoinCondition = targetJoinCondition;
    }

    public Integer getTargetDeleteType() {
        return targetDeleteType;
    }

    public void setTargetDeleteType(Integer targetDeleteType) {
        this.targetDeleteType = targetDeleteType;
    }

    public String getTargetWhereCondition() {
        return targetWhereCondition;
    }

    public void setTargetWhereCondition(String targetWhereCondition) {
        this.targetWhereCondition = targetWhereCondition;
    }

    public Integer getTargetTableType() {
        return targetTableType;
    }

    public void setTargetTableType(Integer targetTableType) {
        this.targetTableType = targetTableType;
    }
}

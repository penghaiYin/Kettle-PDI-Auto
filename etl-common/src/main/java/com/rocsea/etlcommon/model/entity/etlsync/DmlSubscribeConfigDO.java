package com.rocsea.etlcommon.model.entity.etlsync;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Author RocSea
 * @Date 2023/5/15
 */
@Table(name = "dml_subscribe_config")
public class DmlSubscribeConfigDO {

    /**
     * 主键
     */
    @Id
    @Column
    private Long id;

    /**
     * 源数据库表 mq topic
     */
    @Column
    private String sourceTableTopic;

    /**
     * 目标业务模块
     */
    @Column
    private Integer targetServiceModule;

    /**
     * 目标数据库名
     */
    @Column
    private String targetSchemaName;

    /**
     * 目标表名
     */
    @Column
    private String targetTableName;

    /**
     * dml语句类型
     */
    @Column
    private Integer operationType;

    /**
     * 是否开启 0不开启 1开启
     */
    @Column
    private Integer enableStatus;

    /**
     * 源表类型 1:一对一 2:一对多 3:多对一
     */
    @Column
    private Integer dmlConsumeType;

    /**
     * 源表用于分片的字段
     */
    @Column
    private String dmlSourceShardingField;

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

    public String getSourceTableTopic() {
        return sourceTableTopic;
    }

    public void setSourceTableTopic(String sourceTableTopic) {
        this.sourceTableTopic = sourceTableTopic;
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

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getDmlConsumeType() {
        return dmlConsumeType;
    }

    public void setDmlConsumeType(Integer dmlConsumeType) {
        this.dmlConsumeType = dmlConsumeType;
    }

    public String getDmlSourceShardingField() {
        return dmlSourceShardingField;
    }

    public void setDmlSourceShardingField(String dmlSourceShardingField) {
        this.dmlSourceShardingField = dmlSourceShardingField;
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
}

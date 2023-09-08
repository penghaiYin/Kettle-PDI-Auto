package com.rocsea.kettlepdi.model.request;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author RocSea
 * @Date 2022/12/2
 */
public class KettleEtlBuildRequest {
    @ApiModelProperty(value = "同步模块", required = true)
    private Integer moduleType;
    @ApiModelProperty(value = "源数据库", required = true)
    private String sourceSchemaName;
    @ApiModelProperty(value = "源表名", required = true)
    private String sourceTableName;
    @ApiModelProperty(value = "目标表名", required = true)
    private String targetTableName;
    @ApiModelProperty(value = "同步模板", required = true)
    private Integer etlTemplateType;
    @ApiModelProperty(value = "源表需要排除的字段", required = false)
    private String sourceExcludeColumns;
    @ApiModelProperty(value = "同步需要映射的字段", required = false)
    private Map<String, String> aliasMapping;
    @ApiModelProperty(value = "同步需要处理的字段", required = false)
    private Map<String, String> columnsFormat;

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
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

    public String getSourceExcludeColumns() {
        return sourceExcludeColumns;
    }

    public void setSourceExcludeColumns(String sourceExcludeColumns) {
        this.sourceExcludeColumns = sourceExcludeColumns;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public Map<String, String> getAliasMapping() {
        return Objects.isNull(aliasMapping) ? new HashMap<>() : new HashMap<>(aliasMapping);
    }

    public void setAliasMapping(Map<String, String> aliasMapping) {
        this.aliasMapping = Objects.isNull(aliasMapping) ? new HashMap<>() : new HashMap<>(aliasMapping);
    }

    public Map<String, String> getColumnsFormat() {
        return Objects.isNull(columnsFormat) ? new HashMap<>() : new HashMap<>(columnsFormat);
    }

    public void setColumnsFormat(Map<String, String> columnsFormat) {
        this.columnsFormat = Objects.isNull(columnsFormat) ? new HashMap<>() : new HashMap<>(columnsFormat);
    }

    public Integer getEtlTemplateType() {
        return etlTemplateType;
    }

    public void setEtlTemplateType(Integer etlTemplateType) {
        this.etlTemplateType = etlTemplateType;
    }
}

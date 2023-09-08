package com.rocsea.etlcommon.model.bo.kettlepdi;
import java.util.*;
/**
 * 作者：yph
 * 创建日期：2023/1/11
 * 类说明：
 */
public class KettleEtlResourceHolder {
    private List<ColumnInfoBO> sourceColumnList;
    private String selectColumn;
    private Long idConnection;
    private String targetTableName;
    private String sourceTableName;
    private String sourceSchemaName;
    private Long idDirectory;
    private Integer moduleType;
    private Map<String, String> aliasMapping;
    private IdMaxTransRelatedBO idMaxTransRelatedBO;
    private IdMaxJobRelatedBO idMaxJobRelatedBO;
    private Map<String, String> columnsFormat;
    private Integer buildType;

    public List<ColumnInfoBO> getSourceColumnList() {
        return Objects.isNull(sourceColumnList) ? new ArrayList<>() : new ArrayList<>(sourceColumnList);
    }

    public void setSourceColumnList(List<ColumnInfoBO> sourceColumnList) {
        this.sourceColumnList = Objects.isNull(sourceColumnList) ? new ArrayList<>() : new ArrayList<>(sourceColumnList);
    }

    public String getSelectColumn() {
        return selectColumn;
    }

    public void setSelectColumn(String selectColumn) {
        this.selectColumn = selectColumn;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceSchemaName() {
        return sourceSchemaName;
    }

    public void setSourceSchemaName(String sourceSchemaName) {
        this.sourceSchemaName = sourceSchemaName;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public Map<String, String> getAliasMapping() {
        return aliasMapping;
    }

    public void setAliasMapping(Map<String, String> aliasMapping) {
        this.aliasMapping = aliasMapping;
    }

    public Long getIdConnection() {
        return idConnection;
    }

    public void setIdConnection(Long idConnection) {
        this.idConnection = idConnection;
    }

    public Long getIdDirectory() {
        return idDirectory;
    }

    public void setIdDirectory(Long idDirectory) {
        this.idDirectory = idDirectory;
    }

    public IdMaxTransRelatedBO getIdMaxTransRelated() {
        return idMaxTransRelatedBO;
    }

    public void setIdMaxTransRelated(IdMaxTransRelatedBO idMaxTransRelatedBO) {
        this.idMaxTransRelatedBO = idMaxTransRelatedBO;
    }

    public IdMaxJobRelatedBO getIdMaxJobRelated() {
        return idMaxJobRelatedBO;
    }

    public void setIdMaxJobRelated(IdMaxJobRelatedBO idMaxJobRelatedBO) {
        this.idMaxJobRelatedBO = idMaxJobRelatedBO;
    }

    public Map<String, String> getColumnsFormat() {
        return Objects.isNull(columnsFormat) ? new HashMap<>() : new HashMap<>(columnsFormat);
    }

    public void setColumnsFormat(Map<String, String> columnsFormat) {
        this.columnsFormat = Objects.isNull(columnsFormat) ? new HashMap<>() : new HashMap<>(columnsFormat);
    }

    public Integer getBuildType() {
        return buildType;
    }

    public void setBuildType(Integer buildType) {
        this.buildType = buildType;
    }
}

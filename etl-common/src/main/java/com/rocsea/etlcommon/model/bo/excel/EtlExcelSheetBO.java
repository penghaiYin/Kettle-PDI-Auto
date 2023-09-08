package com.rocsea.etlcommon.model.bo.excel;

import java.util.*;

/**
 * @Author RocSea
 * @Date 2023/5/11
 */
public class EtlExcelSheetBO {
    /**
     * 源表名
     */
    private String sourceTableName;
    /**
     * 源数据库名
     */
    private String sourceSchemaName;
    /**
     * 源表中文描述
     */
    private String sourceTableDesc;
    /**
     * 业务模块
     */
    private String serviceModule;
    /**
     * 目标表名
     */
    private String targetTableName;
    /**
     * 是否有物理删除
     */
    private Boolean hasPhysicalDel;
    /**
     * 是否有逻辑删，默认false
     */
    private Boolean hasLogicDel = Boolean.FALSE;
    /**
     * 有用的字段
     */
    private Map<String, String> usefulFieldMap;
    /**
     * 字段列表
     */
    private List<EtlExcelColumnBO> etlExcelColumnBOList;

    private Integer etlTemplateType;

    /**
     * 需要格式化的字段
     */
    private Map<String, String> etlExcelFormatColumnMap;

    public String getSourceTableDesc() {
        return sourceTableDesc;
    }

    public void setSourceTableDesc(String sourceTableDesc) {
        this.sourceTableDesc = sourceTableDesc;
    }

    public String getSourceSchemaName() {
        return sourceSchemaName;
    }

    public void setSourceSchemaName(String sourceSchemaName) {
        this.sourceSchemaName = sourceSchemaName;
    }

    public String getServiceModule() {
        return serviceModule;
    }

    public void setServiceModule(String serviceModule) {
        this.serviceModule = serviceModule;
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

    public Boolean getHasPhysicalDel() {
        return hasPhysicalDel;
    }

    public void setHasPhysicalDel(Boolean hasPhysicalDel) {
        this.hasPhysicalDel = hasPhysicalDel;
    }

    public List<EtlExcelColumnBO> getEtlExcelColumnList() {
        return Objects.isNull(etlExcelColumnBOList) ? new ArrayList<>() : new ArrayList<>(etlExcelColumnBOList);
    }

    public void setEtlExcelColumnList(List<EtlExcelColumnBO> etlExcelColumnBOList) {
        this.etlExcelColumnBOList = Objects.isNull(etlExcelColumnBOList) ? new ArrayList<>() : new ArrayList<>(etlExcelColumnBOList);
    }

    public Boolean getHasLogicDel() {
        return hasLogicDel;
    }

    public void setHasLogicDel(Boolean hasLogicDel) {
        this.hasLogicDel = hasLogicDel;
    }

    public String getSourceColumnName(String targetColumnName) {
        final String sourceColumnName = usefulFieldMap.get(targetColumnName);
        return Objects.isNull(sourceColumnName) ? "" : sourceColumnName;
    }

    public void setUsefulFieldMap(Map<String, String> usefulFieldMap) {
        this.usefulFieldMap = Objects.isNull(usefulFieldMap) ? new HashMap<>() : new HashMap<>(usefulFieldMap);
    }

    public Integer getEtlTemplateType() {
        return etlTemplateType;
    }

    public void setEtlTemplateType(Integer etlTemplateType) {
        this.etlTemplateType = etlTemplateType;
    }

    public Map<String, String> getEtlExcelFormatColumnMap() {
        return Objects.isNull(etlExcelFormatColumnMap) ? new HashMap<>() : new HashMap<>(etlExcelFormatColumnMap);
    }

    public void setEtlExcelFormatColumnMap(Map<String, String> etlExcelFormatColumnMap) {
        this.etlExcelFormatColumnMap = Objects.isNull(etlExcelFormatColumnMap) ? new HashMap<>() : new HashMap<>(etlExcelFormatColumnMap);
    }
}

package com.rocsea.etlcommon.model.bo.excel;

/**
 * @Author RocSea
 * @Date 2023/5/11
 */
public class EtlExcelColumnBO {
    /**
     * 原字段英文名
     */
    private String sourceColumnEnName;
    /**
     * 原字段数据类型
     */
    private String sourceDataType;
    /**
     * 原字段中文名
     */
    private String sourceColumnChName;
    /**
     * 是否保留
     */
    private String hasReserve;
    /**
     * 建议字段英文名
     */
    private String suggestedEnName;
    /**
     * 建议字段数据类型
     */
    private String suggestedDataType;
    /**
     * 建议字段中文名
     */
    private String suggestedChName;

    public String getSourceColumnEnName() {
        return sourceColumnEnName;
    }

    public void setSourceColumnEnName(String sourceColumnEnName) {
        this.sourceColumnEnName = sourceColumnEnName;
    }

    public String getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(String sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getSourceColumnChName() {
        return sourceColumnChName;
    }

    public void setSourceColumnChName(String sourceColumnChName) {
        this.sourceColumnChName = sourceColumnChName;
    }

    public String getHasReserve() {
        return hasReserve;
    }

    public void setHasReserve(String hasReserve) {
        this.hasReserve = hasReserve;
    }

    public String getSuggestedEnName() {
        return suggestedEnName;
    }

    public void setSuggestedEnName(String suggestedEnName) {
        this.suggestedEnName = suggestedEnName;
    }

    public String getSuggestedDataType() {
        return suggestedDataType;
    }

    public void setSuggestedDataType(String suggestedDataType) {
        this.suggestedDataType = suggestedDataType;
    }

    public String getSuggestedChName() {
        return suggestedChName;
    }

    public void setSuggestedChName(String suggestedChName) {
        this.suggestedChName = suggestedChName;
    }
}

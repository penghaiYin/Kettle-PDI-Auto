package com.rocsea.etlcommon.model.enums;
/**
 * 消费类型 枚举
 *
 * @author rocsea
 **/
public enum DatadictModuleEnum {
    /**
     * 国内
     */
    INTGERNAL(1, "国内"),

    /**
     * 国际
     */
    INTGERNATIONAL(2, "国际");


    private final Integer value;
    private final String text;

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    DatadictModuleEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }
}
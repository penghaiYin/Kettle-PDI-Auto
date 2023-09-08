package com.rocsea.etlcommon.model.enums;

/**
 * 启用状态枚举
 *
 * @author rocsea
 **/
public enum EnableEnum {

    ENABLE(1, "启用"),

    DISABLE(0, "禁用");

    private final int value;
    private final String text;

    EnableEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
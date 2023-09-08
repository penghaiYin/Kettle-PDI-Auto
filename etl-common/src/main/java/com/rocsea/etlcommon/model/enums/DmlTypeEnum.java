package com.rocsea.etlcommon.model.enums;

/**
 * dml type enum
 *
 * @author godliu
 **/
public enum DmlTypeEnum {

    /**
     * 新增操作
     */
    INSERT(1, "新增"),
    /**
     * 更新操作
     */
    UPDATE(2, "更新"),
    /**
     * 删除操作
     */
    DELETE(3, "删除");

    private final String text;
    private final Integer value;

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }

    DmlTypeEnum(Integer value, String text) {
        this.text = text;
        this.value = value;
    }
}
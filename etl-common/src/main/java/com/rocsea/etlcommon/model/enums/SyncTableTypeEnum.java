package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.Objects;

/**
 * 同步模块
 * @Author RocSea
 * @Date 2022/12/7
 */
public enum SyncTableTypeEnum {
    /**
     * 单表
     */
    SINGLE_TABLE(1, "单表"),
    /**
     * 多表
     */
    MULTIPLE_TABLES(2, "多表");

    private final String text;
    private final Integer value;

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }

    SyncTableTypeEnum(Integer value, String text) {
        this.text = text;
        this.value = value;
    }

    public static String geText(Integer value) {
        for (SyncTableTypeEnum syncModuleEnum : SyncTableTypeEnum.values()) {
            if (Objects.equals(syncModuleEnum.value, value)) {
                return syncModuleEnum.text;
            }
        }
        throw new BusinessException("module type not match");
    }
}

package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.Objects;

/**
 * 同步模块
 * @Author RocSea
 * @Date 2022/12/7
 */
public enum DeleteTypeEnum {
    /**
     * 单表
     */
    LOGIC(1, "逻辑删除"),
    /**
     * 多表
     */
    PHYSICAL(2, "物理删除");

    private final String text;
    private final Integer value;

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }

    DeleteTypeEnum(Integer value, String text) {
        this.text = text;
        this.value = value;
    }

    public static String geText(Integer value) {
        for (DeleteTypeEnum syncModuleEnum : DeleteTypeEnum.values()) {
            if (Objects.equals(syncModuleEnum.value, value)) {
                return syncModuleEnum.text;
            }
        }
        throw new BusinessException("module type not match");
    }
}

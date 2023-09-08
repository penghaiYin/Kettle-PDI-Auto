package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.Objects;

/**
 * 同步模块
 * @Author RocSea
 * @Date 2022/12/7
 */
public enum KettleModuleEnum {
    /**
     * 国际
     */
    INTERNATIONAL(1, "internation"),
    /**
     * 国内
     */
    DM(2, "dm"),
    /**
     * 国内重构
     */
    ONSHORE(6, "onshore"),
    /**
     * 新三方
     */
    THIRD(41, "third");

    private final String text;
    private final Integer value;

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }

    KettleModuleEnum(Integer value, String text) {
        this.text = text;
        this.value = value;
    }

    public static String geText(Integer value) {
        for (KettleModuleEnum kettleModuleEnum : KettleModuleEnum.values()) {
            if (Objects.equals(kettleModuleEnum.value, value)) {
                return kettleModuleEnum.text;
            }
        }
        throw new BusinessException("module type not match");
    }
}

package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.Objects;

/**
 * 同步模块
 * @Author RocSea
 * @Date 2022/12/7
 */
public enum ServiceModuleEnum {
    /**
     * 国内重构
     */
    ONSHORE(1, "国内重构"),
    /**
     * 老国内
     */
    DM(2, "老国内"),
    /**
     * 国际
     */
    INTERNATIONAL(3, "国际"),
    /**
     * 新三方
     */
    THIRD(4, "新三方"),
    /**
     * 卖出
     */
    SELL_OUT(5, "卖出");

    private final String text;
    private final Integer value;

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }

    ServiceModuleEnum(Integer value, String text) {
        this.text = text;
        this.value = value;
    }

    public static String geText(Integer value) {
        for (ServiceModuleEnum syncModuleEnum : ServiceModuleEnum.values()) {
            if (Objects.equals(syncModuleEnum.value, value)) {
                return syncModuleEnum.text;
            }
        }
        throw new BusinessException("module type not match");
    }
}

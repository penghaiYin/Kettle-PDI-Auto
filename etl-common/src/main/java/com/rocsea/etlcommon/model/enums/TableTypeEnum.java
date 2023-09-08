package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.Objects;

/**
 * 同步模块
 * @Author RocSea
 * @Date 2022/12/7
 */
public enum TableTypeEnum {
    /**
     * mysql
     */
    MYSQL(1, "mysql"),
    /**
     * oracle
     */
    ORACLE(2, "oracle"),
    /**
     * sqlserver
     */
    SQLSERVER(3, "sqlserver");

    private final String text;
    private final Integer value;

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }

    TableTypeEnum(Integer value, String text) {
        this.text = text;
        this.value = value;
    }

    public static String geText(Integer value) {
        for (TableTypeEnum syncModuleEnum : TableTypeEnum.values()) {
            if (Objects.equals(syncModuleEnum.value, value)) {
                return syncModuleEnum.text;
            }
        }
        throw new BusinessException("module type not match");
    }
}

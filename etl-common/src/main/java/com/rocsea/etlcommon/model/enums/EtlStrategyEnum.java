package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.Arrays;

/**
 * 同步类型枚举
 * @Author RocSea
 * @Date 2022/12/7
 */
public enum EtlStrategyEnum {
    /**
     * 单表一对一
     */
    SINGLE(1, "单表"),
    /**
     * 按年月分表一对一
     */
    YEAR_MONTH(2, "按年月分表，一对一");

    private final String text;
    private final Integer value;

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }

    EtlStrategyEnum(Integer value, String text) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取枚举
     *
     * @param value value
     * @return 枚举对象
     */
    public static EtlStrategyEnum getBuildEnum(Integer value) {
        return Arrays.stream(EtlStrategyEnum.values())
                .filter(x -> x.getValue().equals(value)).findFirst()
                .orElseThrow(
                        () -> new BusinessException(String.format("get TableBuildTypeEnum 错误 : %s", value)));
    }
}

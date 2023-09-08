package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.Arrays;

/**
 * 消费类型 枚举
 *
 * @author rocsea
 **/
public enum ConsumeTypeEnum {
    /**
     * 一对一
     */
    CONSUME_TYPE_ONE_TO_ONE(1, "一对一消费类型"),

    /**
     * 一对多消费类型
     */
    CONSUME_TYPE_ONE_TO_MANY(2, "一对多消费类型"),

    /**
     * 多对多消费类型
     */
    CONSUME_TYPE_MANY_TO_MANY(3, "多对多消费类型");


    private final Integer value;
    private final String text;

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    ConsumeTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取 枚举
     *
     * @param value value
     * @return 枚举对象
     */
    public static ConsumeTypeEnum getConsumeTypeEnum(Integer value) {
        return Arrays.stream(ConsumeTypeEnum.values())
                .filter(x -> x.getValue().equals(value)).findFirst()
                .orElseThrow(
                        () -> new BusinessException(String.format("get subscribe type 错误 : %s", value)));
    }
}
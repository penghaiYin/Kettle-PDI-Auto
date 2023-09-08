package com.rocsea.etlcommon.model.enums;

import com.rocsea.etlcommon.exception.BusinessException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
public enum KettleEtlTemplateEnum {
    SIMPLE(1, "simple"),
    FIRST_BATCH(2, "first_batch"),
    SHARDING_ANNUALLY_LIGHT(3, "sharding_annually_light"),
    SHARDING_ANNUALLY_HEAVY(4, "sharding_annually_heavy"),
    SIMPLE_REMOVE_UPDATE_TIME_ID(5, "simple_remove_updateTimeId"),
    FIRST_BATCH_REMOVE_UPDATE_TIME_ID(6, "first_batch_remove_updateTimeId"),
    PAGING_UPDATE(7, "abs_bond_basic_info"),
    PK_UUID(8, "t_bond_cred_chan");

    private final Integer value;
    private final String text;

    // 使用静态map存储所有的枚举值，以便于快速查找
    private static final Map<Integer, KettleEtlTemplateEnum> VALUE_MAP = new HashMap<>();

    static {
        for (KettleEtlTemplateEnum template : KettleEtlTemplateEnum.values()) {
            VALUE_MAP.put(template.value, template);
        }
    }

    KettleEtlTemplateEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static String geText(Integer value) {
        KettleEtlTemplateEnum template = VALUE_MAP.get(value);
        if (template != null) {
            return template.getText();
        }
        throw new BusinessException("Strategy type " + value + " does not match any known template.");
    }
}

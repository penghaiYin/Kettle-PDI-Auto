package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.model.enums.EtlStrategyEnum;

import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.*;

/**
 * Excel语义化工具
 * @Author RocSea
 * @Date 2023/5/15
 */
public final class ExcelSemanticsUtils {
    private static final String NEGATIVE = "否";
    private static final String ABSENT = "没有";
    private static final String NONE = "无";
    private static final String YEAR_MONTH = "_年份_月份";
    private final String content;
    private ExcelSemanticsUtils(String content) {
        this.content = content.trim();
    }

    /**
     * 构造方法，并初始化赋值
     * @param content 内容
     * @return ExcelSemanticsUtils
     */
    public static ExcelSemanticsUtils of(String content) {
        return new ExcelSemanticsUtils(content);
    }

    /**
     * 是否有物理删除
     * @return boolean
     */
    public boolean hasPhysicalDelete() {
        if (NEGATIVE.equals(content) || ABSENT.equals(content) || NONE.equals(content)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 是否保留
     * @return boolean
     */
    public boolean isReserve() {
        if (NEGATIVE.equals(content.trim())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 是否需要解析
     * @return boolean
     */
    public boolean isRequiredParse(){
        if(CREATE_TIME.equals(content) || UPDATE_TIME.equals(content) || DELETED.equals(content)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Integer getTableType() {
        if(content.endsWith(YEAR_MONTH)){
            return EtlStrategyEnum.YEAR_MONTH.getValue();
        }
        return EtlStrategyEnum.SINGLE.getValue();
    }
}


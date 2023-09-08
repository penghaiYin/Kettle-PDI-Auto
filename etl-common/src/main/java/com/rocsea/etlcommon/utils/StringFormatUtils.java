package com.rocsea.etlcommon.utils;

import java.util.Objects;

import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.SQL_VALUE_NULL;

/**
 * 字符串格式化工具类
 * @Author RocSea
 * @Date 2022/7/18
 */
public final class StringFormatUtils {
    private StringFormatUtils() {
    }

    /**
     * 插入语句值格式化
     * @param fieldName 表字段
     * @return 字符串
     */
    public static String sqlValueFormat(String fieldName){
        String formatName = ", '" + fieldName + '\'';
        return fieldName != null ? formatName : SQL_VALUE_NULL;
    }

    /**
     * 对特殊字符转义
     * @param script 脚本语言
     * @return 转义后的字符串
     */
    public static String specialCharacterEscape(String script){
        if(Objects.isNull(script)){
           return null;
        }
        return script.replace("\r", "\\r").replace("\n","\\n").replace("'", "\\'");
    }
}

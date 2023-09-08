package com.rocsea.etlcommon.model.constant;

/**
 * sql构建常量
 * @Author RocSea
 * @Date 2023/1/17
 */
public final class MysqlScriptConstant {
    private MysqlScriptConstant(){
        // hide constructor
    }
    public static final String SELECT_PREFIX = "select ";
    public static final String COMMA = ", ";
    public static final String SINGLE_QUOTE = "'";
    public static final String LINE_FEED = "\n";
    public static final String SQL_VALUE_NULL = ", null";

    public static final String NOT_NULL = "NOT NULL";
    public static final String DEFAULT_NULL = "DEFAULT NULL";
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String UPDATE_TIME = "update_time";
    public static final String CREATE_TIME = "create_time";
    public static final String DELETED = "deleted";
    public static final String NULL = "NULL";
    public static final String COMMENT = "COMMENT";
    public static final String ID = "id";


}

package com.rocsea.etlcommon.model.constant;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public final class KettleTaskConfigBuildConstant {
    private KettleTaskConfigBuildConstant(){
        // hide constructor
    }
    public static final String END_TIME = "2099-12-31 23:59:59";
    public static final String START_TIME = "1970-01-01 00:00:00";
    public static final String SYNC_CONFIG_SCHEMA_NAME = "etl_sync";
    public static final String SYNC_CONFIG_TABLE_NAME = "kettle_task_config";
    public static final String COLUMN_TASK_NAME = "task_name";
}

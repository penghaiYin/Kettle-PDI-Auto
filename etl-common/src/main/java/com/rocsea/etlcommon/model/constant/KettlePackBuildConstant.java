package com.rocsea.etlcommon.model.constant;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public final class KettlePackBuildConstant {
    private KettlePackBuildConstant(){
        // hide constructor
    }
    public static final String KETTLE_PACK_SCHEMA_NAME = "kettle-pack";
    public static final String KETTLE_PACK_TABLE_NAME = "kp_job";
    public static final String KP_JOB_COLUMN_JOB_NAME = "JOB_NAME";
    public static final String KP_JOB_REPOSITORY_ID = "0cc07c24bb1cf0db998c175b4195c60b";
    public static final String CRON_MINUTE_LEVEL = "ss */mm * * * ?";
    public static final String CRON_ONE_HOUR_LEVEL = "ss mm */1 * * ?";
    public static final String SHARDING_CONFIG_PATH = "/common/initialize_sharding_kettle_config/start_sync_config_of_sharding";
}

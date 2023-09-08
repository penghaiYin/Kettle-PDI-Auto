package com.rocsea.etlcommon.model.constant;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
public final class KettleEtlConstant {
    private KettleEtlConstant() {
        // hide constructor
    }
    /**
     * step
     */
    public static final String STEP_NAME_QUERY_FROM_TABLE_NAME = "query_from_tablename";
    public static final String STEP_NAME_INSERT_OR_UPDATE = "insert_or_update";
    public static final String STEP_NAME_TABLE_OUT = "table_out";
    public static final String STEP_NAME_BATCH_INSERT = "batch_insert";
    public static final String STEP_NAME_BATCH_UPDATE = "batch_update";
    public static final String STEP_NAME_TABLE_INPUT = "table_input";

    /**
     * step attribute
     */
    public static final String STEP_ATTRIBUTE_CODE_ROW_META = "row-meta";
    public static final String STEP_ATTRIBUTE_CODE_TRANS_NAME = "trans_name";
    public static final String STEP_ATTRIBUTE_CODE_SQL = "sql";
    public static final String STEP_ATTRIBUTE_CODE_VALUE_NAME = "value_name";
    public static final String STEP_ATTRIBUTE_CODE_VALUE_RENAME = "value_rename";
    public static final String STEP_ATTRIBUTE_CODE_COLUMN_NAME = "column_name";
    public static final String STEP_ATTRIBUTE_CODE_STREAM_NAME = "stream_name";
    public static final String STEP_ATTRIBUTE_CODE_VALUE_UPDATE = "value_update";
    public static final String STEP_ATTRIBUTE_ID_CONNECTION = "id_connection";
    public static final String STEP_ATTRIBUTE_KEY_FIELD = "key_field";

    public static final String DATE_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";
    public static final String JOBENTRY_ATTRIBUTE_CODE_NAME = "name";
    public static final String DEFAULT_REMOVE_COLUMN_UPDATE_BY = "update_by";
    public static final String DEFAULT_REMOVE_COLUMN_CREATE_BY = "create_by";
    public static final String DEFAULT_REMOVE_COLUMN_UPDATED_BY = "updated_by";
    public static final String DEFAULT_REMOVE_COLUMN_CREATED_BY = "created_by";
}

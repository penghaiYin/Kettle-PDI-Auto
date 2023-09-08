package com.rocsea.etlcommon.model.constant;

/**
 * @Author RocSea
 * @Date 2023/5/15
 */
public final class EtlConstant {

    private EtlConstant() {}

    public static final String DEFAULT_SELL_DATABASE = "bond_ext_etl";
    public static final String ETL_KETTLE_PATH = "etl-kettle\\V%d__%s.sql";
    public static final String ETL_TABLE_MYSQL_PATH = "etl-table\\mysql\\V%d__%s.sql";
    public static final String ETL_TABLE_ORACLE_PATH = "etl-table\\oracle\\V%d__%s.sql";
    public static final String ETL_TABLE_SQLSERVER_PATH = "etl-table\\sqlserver\\V%d__%s.sql";
    public static final String ETL_CONFIG_PATH = "etl-config\\Vxx__%s.sql";
    public static final String YEAR_MONTH = "_年份_月份";
    public static final String YEAR_MONTH_REPLACE = "_20";

    public static final String NO_DELETED_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time <= " +
            "\\'%s\\'\\\",\\\"invalid\\\":\\\"\\\"}}";

    public static final String HAS_DELETED_PHYSICAL_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time <= " +
            "\\'%s\\' and deleted = 0 \\\",\\\"invalid\\\":\\\"\\\"}}";

    public static final String HAS_DELETED_PHYSICAL_REVERSE_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time " +
            "<= \\'%s\\' and deleted = 1 \\\",\\\"invalid\\\":\\\"\\\"}}";

    public static final String LOGIC_DELETED_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time <= " +
            "\\'%s\\' and deleted = 0 \\\",\\\"invalid\\\":\\\"update_time <=\\'%s\\' and deleted = 1 \\\"}}";

    public static final String LOGIC_DELETED_REVERSE_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time <= " +
            "\\'%s\\' and deleted = 1 \\\",\\\"invalid\\\":\\\"update_time <=\\'%s\\' and deleted = 0 \\\"}}";

    public static final String YEAR_MONTH_NO_DELETED_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time" +
            ">= " + "\\'%s\\' and update_time <= \\'%s\\' \\\",\\\"invalid\\\":\\\"\\\",\\\"bigTable\\\":1}}";

    public static final String YEAR_MONTH_HAS_DELETED_LOGIC_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time>=" +
            " \\'%s\\' and update_time <= \\'%s\\' and deleted = 0 \\\",\\\"invalid\\\":\\\"update_time>= \\'%s\\' and update_time <= " +
            "\\'%s\\' and deleted = 1\\\",\\\"bigTable\\\":1 }}";

    public static final String YEAR_MONTH_HAS_DELETED_PHYSICAL_SOURCE_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid" +
            "\\\":\\\"update_time>= \\'%s\\' and update_time <= \\'%s\\' and deleted = 0 \\\",\\\"invalid\\\":\\\"\\\",\\\"bigTable\\\":1" +
            " }}";

    public static final String YEAR_MONTH_TARGET_WHERE_CONDITION = "{\\\"condition\\\":{\\\"valid\\\":\\\"update_time>= \\'%s\\'" +
            " and update_time <= \\'%s\\' and deleted = 0 \\\",\\\"invalid\\\":\\\"update_time>= \\'%s\\' and update_time <= \\'%s\\' and" +
            " deleted = 1\\\",\\\"bigTable\\\":1 }}";
}

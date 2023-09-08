package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.model.bo.kettlepdi.ColumnInfoBO;
import com.rocsea.etlcommon.model.JdbcConfig;

import java.util.List;

/**
 * JDBC工具类
 *
 * @Author RocSea
 * @Date 2022/12/2
 */
public final class CheckedDatabaseUtils {
    private static final DatabaseUtils databaseUtils = new DatabaseUtils();

    private CheckedDatabaseUtils() {
        // hide constructor
    }

    /**
     * 获取列信息
     *
     * @param jdbcConfig jdbc信息
     * @param schemaName 方案或数据库名
     * @param tableName  表名
     * @return 获取列信息
     */
    public static List<ColumnInfoBO> listColumnInfos(JdbcConfig jdbcConfig, String schemaName, String tableName) {
        checkSchemaAndTableName(schemaName, tableName);
        final String url = jdbcConfig.getUrl();
        final String username = jdbcConfig.getUsername();
        final String password = jdbcConfig.getPassword();
        return databaseUtils.listColumnInfos(url, username, password, schemaName, tableName);
    }

    /**
     * 分页时，获取下一次分页的起始ID
     *
     * @param jdbcConfig    jdbc信息
     * @param schemaName   方案或数据库名
     * @param tableName    表名
     * @param id           主键ID值
     * @param limitSize    分页大小
     * @return 获取表名集合
     */
    public static Long getTableNextLimitId(JdbcConfig jdbcConfig, String schemaName, String tableName, Long id, Integer limitSize) {
        checkSchemaAndTableName(schemaName, tableName);
        final String url = jdbcConfig.getUrl();
        final String username = jdbcConfig.getUsername();
        final String password = jdbcConfig.getPassword();
        return databaseUtils.getTableNextLimitId(url, username, password, schemaName, tableName, id, limitSize);
    }

    private static void checkSchemaAndTableName(String schemaName, String tableName) {
        // Verify that schemaName and tableName are safe
        if (!schemaName.matches("[A-Za-z0-9_-]+") || !tableName.matches("[A-Za-z0-9_-]+")) {
            throw new IllegalArgumentException("Invalid schema name or table name");
        }
    }
}

package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.exception.BusinessException;
import com.rocsea.etlcommon.exception.SqlRuntimeException;
import com.rocsea.etlcommon.model.bo.kettlepdi.ColumnInfoBO;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * JDBC工具类
 *
 * @Author RocSea
 * @Date 2022/12/2
 */
public final class DatabaseUtils {
    private static final String SELECT_COLUMNS = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

    /**
     * 获取列信息
     *
     * @param jdbcUrl      jdbc url
     * @param jdbcUsername jdbc 用户名
     * @param jdbcPassword jdbc 密码
     * @param schemaName   方案或数据库名
     * @param tableName    表名
     * @return 获取列信息
     */
    public List<ColumnInfoBO> listColumnInfos(String jdbcUrl, String jdbcUsername, String jdbcPassword, String schemaName, String tableName) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement statement = conn.prepareStatement(SELECT_COLUMNS)) {
            statement.setString(1, schemaName);
            statement.setString(2, tableName);
            final List<ColumnInfoBO> result = new ArrayList<>();
            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final ColumnInfoBO columnInfoBO = new ColumnInfoBO();
                    final String columnName = resultSet.getString("COLUMN_NAME");
                    final String columnType = resultSet.getString("COLUMN_TYPE");
                    final String columnKey = resultSet.getString("COLUMN_KEY");
                    final String dataType = resultSet.getString("DATA_TYPE");
                    final String columnComment = resultSet.getString("COLUMN_COMMENT");
                    columnInfoBO.setColumnName(columnName);
                    columnInfoBO.setColumnType(columnType);
                    columnInfoBO.setColumnComment(columnComment);
                    columnInfoBO.setPk("PRI".equalsIgnoreCase(columnKey));
                    columnInfoBO.setDataType(dataType);
                    result.add(columnInfoBO);
                }
                return result;
            }
        } catch (Exception ex) {
            throw new SqlRuntimeException(ex);
        }
    }

    /**
     * 获取表数量
     *
     * @param jdbcUrl      jdbc url
     * @param jdbcUsername jdbc 用户名
     * @param jdbcPassword jdbc 密码
     * @param schemaName   方案或数据库名
     * @param tableName    表名
     * @param params       字段参数
     * @return 获取表主键
     */
    public Optional<Long> getTableCount(String jdbcUrl,
                                        String jdbcUsername,
                                        String jdbcPassword,
                                        String schemaName,
                                        String tableName,
                                        Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("SELECT COUNT(0) AS COUNT FROM `%s`.`%s` WHERE 1=1", schemaName, tableName));
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof Long || entry.getValue() instanceof Integer) {
                    sql.append(" and ").append(entry.getKey()).append(" = ").append(entry.getValue());
                } else if (entry.getValue() instanceof String) {
                    sql.append(" and ").append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
                } else {
                    throw new BusinessException("query param type not support ");
                }
            }
        }
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final long count = resultSet.getLong("COUNT");
                    return Optional.of(count);
                }
                return Optional.empty();
            }
        } catch (Exception ex) {
            throw new SqlRuntimeException(ex);
        }
    }

    /**
     * 获取创表语句
     *
     * @param jdbcUrl      jdbc url
     * @param jdbcUsername jdbc 用户名
     * @param jdbcPassword jdbc 密码
     * @param schemaName   方案或数据库名
     * @param tableName    表名
     * @return String
     */
    public String getTableDesc(String jdbcUrl, String jdbcUsername, String jdbcPassword, String schemaName, String tableName) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement statement = conn.prepareStatement("SHOW CREATE TABLE " + schemaName + "." + tableName)) {

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Create Table");
                }
            }
            return null;
        } catch (Exception ex) {
            throw new SqlRuntimeException(ex);
        }
    }


    /**
     * 获取表名集合
     *
     * @param jdbcUrl      jdbc url
     * @param jdbcUsername jdbc 用户名
     * @param jdbcPassword jdbc 密码
     * @param schemaName   方案或数据库名
     * @param tableName    表名
     * @return 获取表名集合
     */
    public List<String> listLikeTableNames(String jdbcUrl,
                                           String jdbcUsername,
                                           String jdbcPassword,
                                           String schemaName,
                                           String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TABLE_NAME FROM information_schema.tables where table_schema='").append(schemaName).append("' AND TABLE_NAME " +
                "LIKE ").append("'").append(tableName).append("%'");
        List<String> tableNameList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String fullTableName = resultSet.getString("TABLE_NAME");
                    tableNameList.add(fullTableName);
                }
                return tableNameList;
            }
        } catch (Exception ex) {
            throw new SqlRuntimeException(ex);
        }
    }

    /**
     * 获取下一次的分页ID
     *
     * @param jdbcUrl      jdbc url
     * @param jdbcUsername jdbc 用户名
     * @param jdbcPassword jdbc 密码
     * @param schemaName   方案或数据库名
     * @param tableName    表名
     * @param id           主键ID值
     * @param limitSize    分页大小
     * @return 获取表名集合
     */
    public Long getTableNextLimitId(String jdbcUrl,
                                    String jdbcUsername,
                                    String jdbcPassword,
                                    String schemaName,
                                    String tableName,
                                    Long id,
                                    Integer limitSize) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT max(a.id) as id from (SELECT id FROM ").append(schemaName).append(".").append(tableName).append(" WHERE id > ?" +
                " LIMIT ?) a");

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            statement.setLong(1, id);
            statement.setInt(2, limitSize);
            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String result = resultSet.getString("id");
                    return Objects.isNull(result) ? null : Long.valueOf(result);
                }
            }
        } catch (Exception ex) {
            throw new SqlRuntimeException(ex);
        }
        return null;
    }

    public List<String> checkUpdateTimeIndex(String url, String username, String password, String schemaName, List<String> sourceTableNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.STATISTICS" +
                " WHERE TABLE_SCHEMA = '").append(schemaName).append("'").append(" AND TABLE_NAME in(");
        for (int i = 0; i < sourceTableNames.size(); i++) {
            sql.append("'").append(sourceTableNames.get(i)).append("'");
            if (i != sourceTableNames.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") AND (COLUMN_NAME = 'update_time' or COLUMN_NAME = 'updatetime' or COLUMN_NAME = 'updated_time')");

        List<String> tableNameList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String fullTableName = resultSet.getString("TABLE_NAME");
                    tableNameList.add(fullTableName);
                }
                return tableNameList;
            }
        } catch (Exception ex) {
            throw new SqlRuntimeException(ex);
        }
    }

    public List<String> checkNoneUpdateTimeColumnTables(String url, String username, String password, String schemaName, List<String> sourceTableNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT table_name FROM information_schema.columns WHERE table_schema = '");
        sql.append(schemaName).append("'").append(" AND table_name in(");
        for (int i = 0; i < sourceTableNames.size(); i++) {
            sql.append("'").append(sourceTableNames.get(i)).append("'");
            if (i != sourceTableNames.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(") AND column_name = 'update_time'");
        List<String> tableNameList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(sql.toString())) {
            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String fullTableName = resultSet.getString("table_name");
                    tableNameList.add(fullTableName);
                }
                return tableNameList;
            }
        } catch (Exception ex) {
            throw new SqlRuntimeException(ex);
        }
    }
}

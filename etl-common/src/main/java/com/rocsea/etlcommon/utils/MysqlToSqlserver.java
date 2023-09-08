package com.rocsea.etlcommon.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MysqlToSqlserver {
    private MysqlToSqlserver() {
    }

    private static final String TABLE_PREFIX = "[dbo]."; // 表前缀，如果需要加上，则在这里设置
    public static final String ALTER_TABLE_HEAD = "ALTER TABLE [dbo].[%s] SET (LOCK_ESCALATION = TABLE)";
    private static final List<String> DEFINE_EXTENDED_PROPERTY = new ArrayList<>(6);
    private static final List<String> DEFINE_PRIMARY_KEY = new ArrayList<>(6);
    private static final Map<String, String> TYPE_MAPPING = new HashMap<>();// Mysql数据类型与Sqlserver数据类型的对照
    public static final String W_D = "(\\w+)\\((\\d+)\\)";
    public static final int GROUP_2 = 2;
    public static final int GROUP_1 = 1;
    public static final String VARCHAR = "varchar";

    static {
        TYPE_MAPPING.put("int", "int");
        TYPE_MAPPING.put("tinyint", "int");
        TYPE_MAPPING.put("smallint", "int");
        TYPE_MAPPING.put("mediumint", "int");
        TYPE_MAPPING.put("bigint", "bigint");
        TYPE_MAPPING.put("float", "decimal(12,6)");
        TYPE_MAPPING.put("date", "date");
        TYPE_MAPPING.put("time", "time");
        TYPE_MAPPING.put("datetime", "datetime2(0)");
        TYPE_MAPPING.put("timestamp", "datetime");
        TYPE_MAPPING.put("year", "int");
        TYPE_MAPPING.put("char", "nvarchar");
        TYPE_MAPPING.put("varchar", "nvarchar");
        TYPE_MAPPING.put("tinytext", "nvarchar(MAX)");
        TYPE_MAPPING.put("text", "nvarchar(MAX)");
        TYPE_MAPPING.put("mediumtext", "nvarchar(MAX)");
        TYPE_MAPPING.put("longtext", "nvarchar(MAX)");
        TYPE_MAPPING.put("binary", "varbinary");
        TYPE_MAPPING.put("varbinary", "varbinary");
        TYPE_MAPPING.put("tinyblob", "varbinary(MAX)");
        TYPE_MAPPING.put("blob", "varbinary(MAX)");
        TYPE_MAPPING.put("mediumblob", "varbinary(MAX)");
        TYPE_MAPPING.put("longblob", "varbinary(MAX)");
        TYPE_MAPPING.put("enum", "nvarchar");
        TYPE_MAPPING.put("set", "nvarchar");
        DEFINE_EXTENDED_PROPERTY.add(0, "GO");
        DEFINE_EXTENDED_PROPERTY.add(1, "EXEC sp_addextendedproperty");
        DEFINE_EXTENDED_PROPERTY.add(2, "'MS_Description', N'%s',");
        DEFINE_EXTENDED_PROPERTY.add(3, "'SCHEMA', N'dbo',");
        DEFINE_EXTENDED_PROPERTY.add(4, "'TABLE', N'%s',");
        DEFINE_EXTENDED_PROPERTY.add(5, "'COLUMN', N'%s'");
        DEFINE_PRIMARY_KEY.add(0, "CONSTRAINT [PK__%s] PRIMARY KEY CLUSTERED ([%s])");
        DEFINE_PRIMARY_KEY.add(1, "WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = ON, ALLOW_ROW_LOCKS = ON, " +
                "ALLOW_PAGE_LOCKS = ON)");
        DEFINE_PRIMARY_KEY.add(2, "ON [PRIMARY]");
        DEFINE_PRIMARY_KEY.add(3, ")");
        DEFINE_PRIMARY_KEY.add(4, "ON [PRIMARY]");
        DEFINE_PRIMARY_KEY.add(5, "GO");
    }

    // 生成Sqlserver的建表语句
    public static String generateSqlserverCreateTableSql(String mysqlCreateTableSql) {
        String sqlserverCreateTableSql = "";
        ArrayList<String> lines = new ArrayList<>();
        String[] mysqlLines = mysqlCreateTableSql.split("\n");

        String tableName = "";
        ArrayList<String> columns = new ArrayList<>();
        Map<String, String> columnComments = new HashMap<>();
        String primaryKey = "";

        // 遍历Mysql的每一行，解析出表名、字段名、数据类型、注释等信息
        for (int i = 0; i < mysqlLines.length; i++) {
            String mysqlLine = mysqlLines[i].trim();
            if (mysqlLine.startsWith("CREATE TABLE")) {
                tableName = parseTableName(mysqlLine);
            } else if (mysqlLine.startsWith("`")) {
                String columnName = parseColumnName(mysqlLine);
                String dataType = parseDataType(mysqlLine);
                String comment = parseComment(i, mysqlLines);
                columnComments.put(columnName, comment);
                String columnSql = generateSqlserverColumnSql(columnName, dataType);
                columns.add(columnSql);
            }
            if (mysqlLine.startsWith("PRIMARY KEY")) {
                primaryKey = parsePrimaryKey(mysqlLines, i);
            }
        }

        // 拼接Sqlserver的建表语句
        lines.add("CREATE TABLE " + TABLE_PREFIX + "[" + tableName + "] (");
        lines.addAll(columns);
        lines.add(generateSqlserverPrimaryKey(primaryKey, tableName));
        lines.add(generateSqlserverCommentSql(columnComments, tableName));
        sqlserverCreateTableSql = String.join("\n", lines);
        return sqlserverCreateTableSql;
    }


    // 解析Mysql的CREATE TABLE语句，获取表名
    private static String parseTableName(String line) {
        String[] tokens = line.split("`");
        return tokens[3];
    }

    // 解析Mysql的字段定义语句，获取字段名
    private static String parseColumnName(String line) {
        String[] tokens = line.split("`");
        return tokens[1];
    }

    // 解析Mysql的字段定义语句，获取数据类型
    private static String parseDataType(String line) {
        line = line.replace("unsigned", "");
        String[] tokens = line.split("`");
        String replace1 = tokens[2].replace("COLLATE utf8mb4_unicode_ci ", "");
        String replace2 = replace1.replace("CHARACTER SET utf8mb4 ", "");
        String[] columnTypes = replace2.toLowerCase().split(" ");
        columnTypes = Arrays.stream(columnTypes)
                .filter(s -> !s.trim().isEmpty())
                .toArray(String[]::new);
        String dataType = columnTypes[0];
        String dataTypeDesc = columnTypes[1] + " " + columnTypes[2];
        if ("default '0'".equals(dataTypeDesc)) {
            dataTypeDesc = "default null";
        }
        dataTypeDesc = dataTypeDesc.replace("default ", "");
        dataType = transferDataType(dataType);
        return dataType + " " + dataTypeDesc;
    }

    private static String transferDataType(String dataType) {
        Pattern pattern = Pattern.compile(W_D);
        Matcher matcher = pattern.matcher(dataType);
        if (matcher.find()) {
            dataType = matcher.group(GROUP_1);// 数据类型
            int len = Integer.parseInt(matcher.group(GROUP_2));// 数据类型的长度
            if (VARCHAR.equals(dataType)) {
                dataType = TYPE_MAPPING.get(dataType) + "(" + len + ")";
            }
        } else {
            String value = TYPE_MAPPING.get(dataType);
            dataType = Objects.isNull(value) ? dataType : value;
        }
        return dataType;
    }

    // 解析Mysql的COMMENT语句，获取注释
    private static String parseComment(int i, String[] mysqlLines) {
        String comment = "";
        String[] commentSplits = mysqlLines[i].split("COMMENT");
        if (commentSplits.length > 1) {
            comment = commentSplits[1].trim().replace("'", "").replace(",", "");
        }
        return comment;
    }

    // 解析Mysql的PRIMARY KEY语句，获取主键字段名
    private static String parsePrimaryKey(String[] mysqlLines, int i) {
        String[] tokens = mysqlLines[i].split("`");
        return tokens[1];
    }

    // 生成etl Sqlserver的字段定义语句
    private static String generateSqlserverColumnSql(String columnName, String dataType) {
        return "[" + columnName + "] " + dataType + ",";
    }

    // 生成etl Sqlserver的主键定义语句
    private static String generateSqlserverPrimaryKey(String primaryKey, String tableName) {
        List<String> lines = new ArrayList<>();
        if (StringUtils.isNotEmpty(primaryKey)) {
            lines.add(String.format(DEFINE_PRIMARY_KEY.get(0), tableName, primaryKey));
            lines.add(DEFINE_PRIMARY_KEY.get(1));
            lines.add(DEFINE_PRIMARY_KEY.get(2));
            lines.add(DEFINE_PRIMARY_KEY.get(3));
            lines.add(DEFINE_PRIMARY_KEY.get(4));
            lines.add(DEFINE_PRIMARY_KEY.get(5));
        }
        return String.join("\n", lines);
    }

    // 生成etl Sqlserver的注释语句
    private static String generateSqlserverCommentSql(Map<String, String> columnComments, String tableName) {
        List<String> lines = new ArrayList<>();
        lines.add(String.format(ALTER_TABLE_HEAD, tableName));
        for (Map.Entry<String, String> entry : columnComments.entrySet()) {
            lines.add(DEFINE_EXTENDED_PROPERTY.get(0));
            lines.add(DEFINE_EXTENDED_PROPERTY.get(1));
            lines.add(String.format(DEFINE_EXTENDED_PROPERTY.get(2), entry.getValue()));
            lines.add(DEFINE_EXTENDED_PROPERTY.get(3));
            lines.add(String.format(DEFINE_EXTENDED_PROPERTY.get(4), tableName));
            lines.add(String.format(DEFINE_EXTENDED_PROPERTY.get(5), entry.getKey()));
        }
        return String.join("\n", lines);
    }
}
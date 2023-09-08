package com.rocsea.kettlepdi.factory.jdbc;

import com.rocsea.etlcommon.model.JdbcConfig;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DM数据源工厂
 * @Author RocSea
 * @Date 2022/12/7
 */
public final class EtlJdbcFactory {
    private EtlJdbcFactory() {
        // hide constructor
    }

    private static final Map<String, JdbcConfig> databaseMap = new ConcurrentHashMap<>();

    /**
     * 注册
     * @param name 名字
     * @param database 数据源
     */
    public static void register(String name, JdbcConfig database) {
        if (!Objects.isNull(database)) {
            databaseMap.put(name, database);
        }
    }

    /**
     * 获取jdbc配置
     * @param name 名字
     * @return JdbcConfig
     */
    public static JdbcConfig getDatabase(String name) {
        return databaseMap.get(name);
    }
}


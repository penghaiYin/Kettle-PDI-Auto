package com.rocsea.etlcommon.model;

import org.springframework.beans.factory.InitializingBean;

/**
 * JDBC 配置
 * @Author RocSea
 * @Date 2022/12/14
 */
public interface JdbcConfig extends InitializingBean {
    String getUrl();
    String getUsername();
    String getPassword();

    /**
     * 名字
     * @return String
     */
    String name();
}

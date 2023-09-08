package com.rocsea.kettlepdi.factory.jdbc;

import com.rocsea.etlcommon.model.JdbcConfig;
import com.rocsea.etlcommon.model.enums.KettleModuleEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 国内重构 JDBC
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
@ConfigurationProperties(prefix = "jdbc.onshore")
public class OnshoreJdbc implements JdbcConfig {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() {
        EtlJdbcFactory.register(name(), this);
    }

    @Override
    public String name() {
        return KettleModuleEnum.ONSHORE.getText();
    }
}

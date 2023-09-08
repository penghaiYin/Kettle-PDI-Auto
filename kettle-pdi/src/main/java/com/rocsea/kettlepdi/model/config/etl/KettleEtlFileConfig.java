package com.rocsea.kettlepdi.model.config.etl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
@ConfigurationProperties(prefix = "kettle.etl.file")
public class KettleEtlFileConfig {
    private String path;
    private Integer serialNum;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }
}

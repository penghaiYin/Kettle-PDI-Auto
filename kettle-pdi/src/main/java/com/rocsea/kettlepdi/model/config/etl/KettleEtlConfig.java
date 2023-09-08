package com.rocsea.kettlepdi.model.config.etl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
@ConfigurationProperties(prefix = "kettle.etl")
public class KettleEtlConfig {
    private final KettleEtlTemplateConfig kettleEtlTemplateConfig;
    private final KettleEtlFileConfig kettleEtlFileConfig;

    public KettleEtlConfig(KettleEtlTemplateConfig kettleEtlTemplateConfig,
                           KettleEtlFileConfig kettleEtlFileConfig) {
        this.kettleEtlTemplateConfig = kettleEtlTemplateConfig;
        this.kettleEtlFileConfig = kettleEtlFileConfig;
    }
    private Boolean enableUpdateTimeId;
    private String outDatabase;

    public String getOutDatabase() {
        return outDatabase;
    }

    public void setOutDatabase(String outDatabase) {
        this.outDatabase = outDatabase;
    }

    public KettleEtlTemplateConfig getKettleBuildTemplate() {
        return kettleEtlTemplateConfig;
    }

    public KettleEtlFileConfig getKettleBuildFile() {
        return kettleEtlFileConfig;
    }

    public Boolean getEnableUpdateTimeId() {
        return enableUpdateTimeId;
    }

    public void setEnableUpdateTimeId(Boolean enableUpdateTimeId) {
        this.enableUpdateTimeId = enableUpdateTimeId;
    }
}

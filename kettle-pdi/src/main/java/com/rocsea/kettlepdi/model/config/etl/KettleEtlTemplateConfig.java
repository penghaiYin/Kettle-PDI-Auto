package com.rocsea.kettlepdi.model.config.etl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
@ConfigurationProperties(prefix = "kettle.etl.template")
public class KettleEtlTemplateConfig {
    private TemplateInfo simple;
    private TemplateInfo simpleRemove;
    private TemplateInfo firstBatchInsert;
    private TemplateInfo firstBatchInsertRemove;
    private TemplateInfo shardingLight;
    private TemplateInfo shardingHeavy;
    private TemplateInfo pagingUpdate;
    private TemplateInfo pkUuid;

    public TemplateInfo getSimple() {
        return simple;
    }

    public void setSimple(TemplateInfo simple) {
        this.simple = simple;
    }

    public TemplateInfo getSimpleRemove() {
        return simpleRemove;
    }

    public void setSimpleRemove(TemplateInfo simpleRemove) {
        this.simpleRemove = simpleRemove;
    }

    public TemplateInfo getFirstBatchInsert() {
        return firstBatchInsert;
    }

    public void setFirstBatchInsert(TemplateInfo firstBatchInsert) {
        this.firstBatchInsert = firstBatchInsert;
    }

    public TemplateInfo getFirstBatchInsertRemove() {
        return firstBatchInsertRemove;
    }

    public void setFirstBatchInsertRemove(TemplateInfo firstBatchInsertRemove) {
        this.firstBatchInsertRemove = firstBatchInsertRemove;
    }

    public TemplateInfo getShardingLight() {
        return shardingLight;
    }

    public void setShardingLight(TemplateInfo shardingLight) {
        this.shardingLight = shardingLight;
    }

    public TemplateInfo getShardingHeavy() {
        return shardingHeavy;
    }

    public void setShardingHeavy(TemplateInfo shardingHeavy) {
        this.shardingHeavy = shardingHeavy;
    }

    public TemplateInfo getPagingUpdate() {
        return pagingUpdate;
    }

    public void setPagingUpdate(TemplateInfo pagingUpdate) {
        this.pagingUpdate = pagingUpdate;
    }

    public TemplateInfo getPkUuid() {
        return pkUuid;
    }

    public void setPkUuid(TemplateInfo pkUuid) {
        this.pkUuid = pkUuid;
    }
}

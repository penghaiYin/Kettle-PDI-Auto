package com.rocsea.kettlepdi.template;

import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import org.springframework.stereotype.Component;

/**
 * 主键是UUID
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
public class PkUuidTemplate extends FirstBatchInsertTemplate {
    @Override
    public TemplateInfo getTemplateInfo() {
        return getEtlTemplateConfig().getPkUuid();
    }

    @Override
    public void afterPropertiesSet() {
        KettleEtlTemplateFactory.register(KettleEtlTemplateEnum.PK_UUID.getText(), this);
    }

}

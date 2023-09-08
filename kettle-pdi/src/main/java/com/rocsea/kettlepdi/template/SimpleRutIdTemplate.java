package com.rocsea.kettlepdi.template;

import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import org.springframework.stereotype.Component;

/**
 * 简单的同步移除update_time_id
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
public class SimpleRutIdTemplate extends SimpleTemplate {
    @Override
    public TemplateInfo getTemplateInfo() {
        return getTemplateConfig().getSimpleRemove();
    }

    @Override
    public void afterPropertiesSet() {
        KettleEtlTemplateFactory.register(KettleEtlTemplateEnum.SIMPLE_REMOVE_UPDATE_TIME_ID.getText(), this);
    }

}

package com.rocsea.kettlepdi.template;

import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import org.springframework.stereotype.Component;

/**
 * first_batch移除update_time_id
 * @Author RocSea
 * @Date 2022/12/7
 *
 */
@Component
public class FirstBatchInsertRutIdTemplate extends FirstBatchInsertTemplate {
    @Override
    public TemplateInfo getTemplateInfo() {
        return getEtlTemplateConfig().getFirstBatchInsertRemove();
    }

    @Override
    public void afterPropertiesSet() {
        KettleEtlTemplateFactory.register(KettleEtlTemplateEnum.FIRST_BATCH_REMOVE_UPDATE_TIME_ID.getText(), this);
    }

}

package com.rocsea.kettlepdi.template;

import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import org.springframework.stereotype.Component;
/**
 * 分页更新
 *
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
public class PagingUpdateTemplate extends FirstBatchInsertTemplate {

    @Override
    public TemplateInfo getTemplateInfo() {
        return getEtlTemplateConfig().getPagingUpdate();
    }


    @Override
    public void afterPropertiesSet() {
        KettleEtlTemplateFactory.register(KettleEtlTemplateEnum.PAGING_UPDATE.getText(), this);
    }

}

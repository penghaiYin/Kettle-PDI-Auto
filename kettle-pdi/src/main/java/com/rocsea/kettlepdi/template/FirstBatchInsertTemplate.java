package com.rocsea.kettlepdi.template;

import com.rocsea.etlcommon.model.entity.kettleresource.StepAttributeDO;
import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.kettlepdi.model.config.etl.KettleEtlTemplateConfig;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 第一次走批量插入
 * @Author RocSea
 * @Date 2022/12/7
 *
 */
@Component
public class FirstBatchInsertTemplate extends AbstractKettleEtlTemplate {
    @Resource
    private KettleEtlTemplateConfig etlTemplateConfig;
    @Override
    public String processTableInput(List<StepAttributeDO> stepAttributeDOS) throws IOException {
        return super.processTableInputWithRowMeta(stepAttributeDOS);
    }

    @Override
    public String processSqlReplace(String sql) {
        return sql.substring(sql.indexOf("FROM"));
    }

    @Override
    public String processQueryFromTableName(List<StepAttributeDO> stepAttributeDOS) throws IOException {
        return super.processTableInputWithRowMeta(stepAttributeDOS);
    }

    @Override
    public void afterPropertiesSet() {
        KettleEtlTemplateFactory.register(KettleEtlTemplateEnum.FIRST_BATCH.getText(), this);
    }

    @Override
    public TemplateInfo getTemplateInfo() {
        return etlTemplateConfig.getFirstBatchInsert();
    }

    public KettleEtlTemplateConfig getEtlTemplateConfig() {
        return etlTemplateConfig;
    }
}

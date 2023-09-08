package com.rocsea.kettlepdi.template;

import com.rocsea.etlcommon.model.entity.kettleresource.StepAttributeDO;
import com.rocsea.etlcommon.model.entity.kettleresource.TransformationDO;
import com.rocsea.etlcommon.model.enums.KettleEtlTemplateEnum;
import com.rocsea.etlcommon.utils.BeanCopyUtils;
import com.rocsea.etlcommon.utils.MysqlBuilderUtils;
import com.rocsea.kettlepdi.model.config.etl.KettleEtlTemplateConfig;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import com.rocsea.kettlepdi.utils.KettleEtlUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 简单的同步
 * @Author RocSea
 * @Date 2022/12/7
 */
@Component
public class SimpleTemplate extends AbstractKettleEtlTemplate {

    @Resource
    private KettleEtlTemplateConfig templateConfig;

    public KettleEtlTemplateConfig getTemplateConfig() {
        return templateConfig;
    }

    @Override
    public TemplateInfo getTemplateInfo() {
        return templateConfig.getSimple();
    }

    @Override
    public String processTransformation(TransformationDO transformationDO) {
        TransformationDO buildDO = BeanCopyUtils.copyProperties(transformationDO, TransformationDO.class);
        buildDO.setIdTransformation(KettleEtlUtils.getIdMaxTransRelated().addAndGetIdTransformationMax());
        buildDO.setIdDirectory(KettleEtlUtils.getIdDirectory());
        buildDO.setName("sync_" + KettleEtlUtils.getSourceTableName());
        return MysqlBuilderUtils.buildTransformation(buildDO);
    }

    @Override
    public String processTableInput(List<StepAttributeDO> stepAttributeDOS) throws IOException {
        return super.processTableInputWithRowMeta(stepAttributeDOS);
    }

    @Override
    public String processSqlReplace(String sql) {
        String replace = sql.replace(getTemplateInfo().getName(), KettleEtlUtils.getSourceTableName());
        return replace.substring(replace.indexOf("FROM"));
    }

    @Override
    public String processQueryFromTableName(List<StepAttributeDO> stepAttributeDOS) throws IOException {
        return super.processTableInputWithRowMeta(stepAttributeDOS);
    }


    @Override
    public void afterPropertiesSet() {
        KettleEtlTemplateFactory.register(KettleEtlTemplateEnum.SIMPLE.getText(), this);
    }

}

package com.rocsea.kettlepdi.template;

import com.rocsea.etlcommon.model.entity.kettleresource.StepAttributeDO;
import com.rocsea.etlcommon.model.entity.kettleresource.TransformationDO;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.List;

/**
 * @Author RocSea
 * @Date 2023/1/17
 */
public interface KettleEtlTemplate extends InitializingBean {
    String processTransformation(TransformationDO transformationDO);

    String processTableInput(List<StepAttributeDO> stepAttributeDOS) throws IOException;

    String processQueryFromTableName(List<StepAttributeDO> stepAttributeDOS) throws IOException;

    String processBatchInsertOrUpdate(List<StepAttributeDO> stepAttributeDOS);

    String processSqlReplace(String valueStr);
    TemplateInfo getTemplateInfo();
}

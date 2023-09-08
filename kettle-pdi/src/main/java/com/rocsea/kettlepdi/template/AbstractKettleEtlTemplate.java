package com.rocsea.kettlepdi.template;



import com.rocsea.etlcommon.model.bo.kettlepdi.IdMaxTransRelatedBO;
import com.rocsea.etlcommon.model.entity.kettleresource.StepAttributeDO;
import com.rocsea.etlcommon.model.entity.kettleresource.TransformationDO;
import com.rocsea.etlcommon.utils.BeanCopyUtils;
import com.rocsea.etlcommon.utils.MysqlBuilderUtils;
import com.rocsea.kettlepdi.model.config.etl.TemplateInfo;
import com.rocsea.kettlepdi.utils.KettleEtlUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.rocsea.etlcommon.model.constant.KettleEtlConstant.*;
import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.*;


/**
 * 抽象的构建模板
 * @Author RocSea
 * @Date 2023/1/30
 */
public abstract class AbstractKettleEtlTemplate implements KettleEtlTemplate {

    @Override
    public String processTransformation(TransformationDO transformationDO) {
        TransformationDO buildDO = BeanCopyUtils.copyProperties(transformationDO, TransformationDO.class);
        buildDO.setIdTransformation(KettleEtlUtils.getIdMaxTransRelated().addAndGetIdTransformationMax());
        buildDO.setIdDirectory(KettleEtlUtils.getIdDirectory());
        buildDO.setName(buildDO.getName().replace(this.getTemplateInfo().getName(), KettleEtlUtils.getSourceTableName()));
        return MysqlBuilderUtils.buildTransformation(buildDO);
    }

    public String processTableInputWithRowMeta(List<StepAttributeDO> stepAttributeDOS) throws IOException {
        StringBuilder sql = new StringBuilder();
        IdMaxTransRelatedBO idMaxTransRelatedBO = KettleEtlUtils.getIdMaxTransRelated();
        for (StepAttributeDO t : stepAttributeDOS) {
            StepAttributeDO buildDO = BeanCopyUtils.copyProperties(t, StepAttributeDO.class);
            buildDO.setIdTransformation(idMaxTransRelatedBO.getIdTransformationMax());
            buildDO.setIdStep(idMaxTransRelatedBO.getIdStepMax());
            switch (t.getCode()) {
                case STEP_ATTRIBUTE_CODE_ROW_META:
                    buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
                    String valueStr = KettleEtlUtils.of(KettleEtlUtils.getSourceColumnList()).getRowMetaXml();
                    buildDO.setValueStr(valueStr);
                    // 构建 r_step_attribute
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
                    break;
                case STEP_ATTRIBUTE_CODE_SQL:
                    String sqlSuffix = this.processSqlReplace(t.getValueStr());
                    buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
                    if(KettleEtlUtils.getSelectColumn().contains("as update_time,")){
                        Optional<Map.Entry<String, String>> updateTimeOptional = KettleEtlUtils.getAliasMapping().entrySet().stream()
                                .filter(x -> x.getValue().equals(UPDATE_TIME)).findFirst();
                        sqlSuffix = sqlSuffix.replace("update_time", updateTimeOptional.get().getKey());
                    }
                    buildDO.setValueStr(SELECT_PREFIX + KettleEtlUtils.getSelectColumn() + " " + sqlSuffix);
                    // 构建 r_step_attribute
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
                    break;
                case STEP_ATTRIBUTE_ID_CONNECTION:
                    buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
                    buildDO.setValueNum(KettleEtlUtils.getIdConnection());
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
                    break;
                default:
                    buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
            }
        }
        return sql.toString();
    }

    public String processTableInputNoRowMeta(List<StepAttributeDO> stepAttributeDOS) {
        IdMaxTransRelatedBO idMaxTransRelatedBO = KettleEtlUtils.getIdMaxTransRelated();
        StringBuilder sql = new StringBuilder();
        for (StepAttributeDO t : stepAttributeDOS) {
            StepAttributeDO buildDO = BeanCopyUtils.copyProperties(t, StepAttributeDO.class);
            buildDO.setIdTransformation(idMaxTransRelatedBO.getIdTransformationMax());
            buildDO.setIdStep(idMaxTransRelatedBO.getIdStepMax());
            switch (t.getCode()) {
                case STEP_ATTRIBUTE_CODE_SQL:
                    String sqlSuffix = this.processSqlReplace(t.getValueStr());
                    buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
                    buildDO.setValueStr(SELECT_PREFIX + KettleEtlUtils.getSelectColumn() + " " + sqlSuffix);
                    // 构建 r_step_attribute
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
                    break;
                case STEP_ATTRIBUTE_ID_CONNECTION:
                    buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
                    buildDO.setValueNum(KettleEtlUtils.getIdConnection());
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
                    break;
                default:
                    buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
            }
        }
        return sql.toString();
    }

    @Override
    public String processBatchInsertOrUpdate(List<StepAttributeDO> stepAttributeDOS) {
        IdMaxTransRelatedBO idMaxTransRelatedBO = KettleEtlUtils.getIdMaxTransRelated();
        StringBuilder sql = new StringBuilder();
        for (StepAttributeDO t : stepAttributeDOS) {
            String valueStr = t.getValueStr();
            StepAttributeDO stepAttributeDO = BeanCopyUtils.copyProperties(t, StepAttributeDO.class);
            stepAttributeDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
            stepAttributeDO.setIdTransformation(idMaxTransRelatedBO.getIdTransformationMax());
            stepAttributeDO.setIdStep(idMaxTransRelatedBO.getIdStepMax());
            if (STEP_ATTRIBUTE_CODE_TRANS_NAME.equals(t.getCode())) {
                valueStr = valueStr.replace(this.getTemplateInfo().getName(), KettleEtlUtils.getSourceTableName());
            }
            stepAttributeDO.setValueStr(valueStr);
            sql.append(MysqlBuilderUtils.buildStepAttribute(stepAttributeDO));
        }
        return sql.toString();
    }

    public abstract TemplateInfo getTemplateInfo();

}

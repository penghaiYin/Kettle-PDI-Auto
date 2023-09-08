package com.rocsea.kettlepdi.builder;

import com.rocsea.etlcommon.exception.BusinessException;
import com.rocsea.etlcommon.exception.IORuntimeException;
import com.rocsea.etlcommon.model.bo.kettlepdi.*;
import com.rocsea.etlcommon.model.entity.kettleresource.*;
import com.rocsea.etlcommon.model.enums.KettleModuleEnum;
import com.rocsea.etlcommon.utils.BeanCopyUtils;
import com.rocsea.etlcommon.utils.MysqlBuilderUtils;
import com.rocsea.kettlepdi.factory.jdbc.EtlJdbcFactory;
import com.rocsea.etlcommon.model.JdbcConfig;
import com.rocsea.kettlepdi.model.config.etl.KettleEtlConfig;
import com.rocsea.kettlepdi.model.request.KettleEtlBuildRequest;
import com.rocsea.kettlepdi.service.kettle.*;
import com.rocsea.kettlepdi.template.KettleEtlTemplate;
import com.rocsea.etlcommon.utils.CheckedDatabaseUtils;
import com.rocsea.kettlepdi.utils.KettleEtlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.rocsea.etlcommon.model.constant.KettleEtlConstant.*;
import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.*;

/**
 * 默认的具体构建者
 *
 * @Author RocSea
 * @Date 2022/12/7
 */
public class DefaultKettleEtlBuilder implements KettleEtlBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultKettleEtlBuilder.class);
    private final KettleEtlTemplate template;
    private final KettleEtlProductBO product = new KettleEtlProductBO();
    @Resource
    private KettleEtlPointService kettleEtlPointService;
    @Resource
    private TransformationService transformationService;
    @Resource
    private JobService jobService;
    @Resource
    private KettleEtlConfig kettleEtlConfig;
    @Resource
    private DatabaseCacheService databaseCacheService;
    private static final int ETL_SYNC_ID_DATABASE = 7;
    private static final int BOND_EXT_ETL_ID_DATABASE = 8;

    /**
     * 构造方法
     * @param template 模板
     */
    public DefaultKettleEtlBuilder(KettleEtlTemplate template) {
        this.template = template;
    }

    @Override
    public void init(KettleEtlBuildRequest request) {
        StringBuilder selectColumns = new StringBuilder();
        HashSet<String> set = new HashSet<>();
        if (!StringUtils.isEmpty(request.getSourceExcludeColumns())) {
            List<String> excludes = Arrays.asList(request.getSourceExcludeColumns().split(","));
            set.addAll(excludes);
        }
        set.add(DEFAULT_REMOVE_COLUMN_CREATE_BY);
        set.add(DEFAULT_REMOVE_COLUMN_CREATED_BY);
        set.add(DEFAULT_REMOVE_COLUMN_UPDATE_BY);
        set.add(DEFAULT_REMOVE_COLUMN_UPDATED_BY);
        JdbcConfig jdbcConfig = EtlJdbcFactory.getDatabase(KettleModuleEnum.geText(request.getModuleType()));
        List<ColumnInfoBO> columnInfoBOS = CheckedDatabaseUtils.listColumnInfos(jdbcConfig, request.getSourceSchemaName(),
                request.getSourceTableName());
        if (columnInfoBOS.isEmpty()) {
            throw new BusinessException("field information of source table is not found");
        }
        List<ColumnInfoBO> needColumns =
                columnInfoBOS.stream().filter(columnInfo -> !set.contains(columnInfo.getColumnName())).collect(Collectors.toList());

        if (request.getColumnsFormat().size() > 0) {
            List<ColumnInfoBO> columnsNotFormat = needColumns.stream().filter(x -> Objects.isNull(request.getColumnsFormat()
                    .get(x.getColumnName()))).collect(Collectors.toList());
            selectColumns.append(columnsNotFormat.stream().map(ColumnInfoBO::getColumnName).collect(Collectors.joining(COMMA)));

            for (Map.Entry<String, String> entry : request.getColumnsFormat().entrySet()) {
                selectColumns.append(COMMA).append(entry.getValue());
            }
        } else {
            String joinColumns = needColumns.stream().map(ColumnInfoBO::getColumnName).collect(Collectors.joining(COMMA));
            selectColumns.append(joinColumns);
        }
        if (kettleEtlConfig.getEnableUpdateTimeId()) {
            selectColumns.append(COMMA).append("0 as update_time_id");
            addUpdateTimeId(needColumns);
        }
        String selectSql = selectColumns.toString();
        // 适配 id、update_time
        for (Map.Entry<String, String> entry : request.getAliasMapping().entrySet()) {
            if (ID.equals(entry.getValue()) || UPDATE_TIME.equals(entry.getValue())) {
                selectSql = selectSql.replace(entry.getKey(), entry.getKey() + " as " + entry.getValue());
            }
        }

        KettleEtlResourceHolder buildResourceHolder = BeanCopyUtils.copyProperties(request, KettleEtlResourceHolder.class);
        buildResourceHolder.setSourceColumnList(needColumns);
        buildResourceHolder.setSelectColumn(selectSql);
        setBuildResourceHolder(buildResourceHolder);
    }

    private void setBuildResourceHolder(KettleEtlResourceHolder buildResourceHolder) {
        KettleEtlUtils.setAliasMapping(buildResourceHolder.getAliasMapping());
        KettleEtlUtils.setModuleType(buildResourceHolder.getModuleType());
        KettleEtlUtils.setSourceSchemaName(buildResourceHolder.getSourceSchemaName());
        KettleEtlUtils.setSourceTableName(buildResourceHolder.getSourceTableName());
        KettleEtlUtils.setTargetTableName(buildResourceHolder.getTargetTableName());
        KettleEtlUtils.setSelectColumn(buildResourceHolder.getSelectColumn());
        KettleEtlUtils.setSourceColumnList(buildResourceHolder.getSourceColumnList());
        KettleEtlUtils.setColumnsFormat(buildResourceHolder.getColumnsFormat());
        KettleEtlUtils.setBuildType(buildResourceHolder.getBuildType());
    }

    @Override
    public void buildResource() {
        StringBuilder resources = new StringBuilder();
        DirectoryPointBO directoryPointBO = kettleEtlPointService.getDirectoryPoint();
        for (DirectoryDO directoryDO : directoryPointBO.getDirectoryList()) {
            resources.append(MysqlBuilderUtils.buildDirectory(directoryDO));
        }
        DatabasePointBO databasePointBO = kettleEtlPointService.getDatabasePoint();
        for (DatabaseDO databaseDO : databasePointBO.getDatabase()) {
            resources.append(MysqlBuilderUtils.buildDatabase(databaseDO));
        }
        for (DatabaseAttributeDO databaseAttributeDO : databasePointBO.getDatabaseAttribute()) {
            resources.append(MysqlBuilderUtils.buildDatabaseAttribute(databaseAttributeDO));
        }
        this.product.setResources(resources.toString());
    }

    @Override
    public void buildTransformation() {
        StringBuilder transformation = new StringBuilder();
        TransformationPointBO transformationPointBO = kettleEtlPointService.getTransformationPoint(this.template.getTemplateInfo().getId());

        for (TransformationDO transformationDO : transformationPointBO.getTransformation()) {
            transformation.append(this.template.processTransformation(transformationDO));
            transformation.append(this.buildTransAttribute(transformationDO.getIdTransformation()));
            try {
                transformation.append(this.buildStepRelated(transformationDO.getIdTransformation()));
            } catch (IOException ex) {
                LOGGER.error("buildStepRelated 构建异常", ex);
                throw new IORuntimeException("buildStepRelated 构建异常");
            }
        }
        this.product.setTransformation(transformation.toString());
    }

    /**
     * 构建转换属性
     * @param idTransformation 转换ID
     * @return String
     */
    public String buildTransAttribute(Long idTransformation) {
        StringBuilder sql = new StringBuilder();
        List<TransAttributeDO> transAttributeDOS = transformationService.listTransAttribute(idTransformation);

        for (TransAttributeDO t : transAttributeDOS) {
            TransAttributeDO buildDO = BeanCopyUtils.copyProperties(t, TransAttributeDO.class);
            buildDO.setIdTransAttribute(KettleEtlUtils.getIdMaxTransRelated().addAndGetIdTransAttributeMax());
            buildDO.setIdTransformation(KettleEtlUtils.getIdMaxTransRelated().getIdTransformationMax());
            sql.append(MysqlBuilderUtils.buildTransAttribute(buildDO));
        }
        return sql.toString();
    }

    /**
     * 构建转换步骤
     * @param templateIdTransformation 模板转换ID
     * @return String
     * @throws IOException IO异常
     */
    public String buildStepRelated(Long templateIdTransformation) throws IOException {
        StringBuilder sql = new StringBuilder();
        List<StepDO> stepDOS = transformationService.listStep(templateIdTransformation);

        // 计算当前idStepMax与模板转换的idStep的差值
        Long idStepMin = stepDOS.get(0).getIdStep();
        Long stepIdDiffer = KettleEtlUtils.getIdMaxTransRelated().getIdStepMax() - idStepMin + 1L;

        for (StepDO stepDO : stepDOS) {
            StepDO stepBuildDO = BeanCopyUtils.copyProperties(stepDO, StepDO.class);
            stepBuildDO.setIdStep(KettleEtlUtils.getIdMaxTransRelated().addAndGetIdStepMax());
            stepBuildDO.setIdTransformation(KettleEtlUtils.getIdMaxTransRelated().getIdTransformationMax());
            // 构建 r_step
            sql.append(MysqlBuilderUtils.buildStep(stepBuildDO));

            // 构建 r_step_attribute
            sql.append(this.buildStepAttribute(stepDO));
        }
        // 构建 r_step_database
        sql.append(this.buildStepDatabase(templateIdTransformation, stepIdDiffer));

        // 构建 r_trans_hop
        sql.append(this.buildTransHop(templateIdTransformation, stepIdDiffer));

        // 构建 r_trans_step_condition
        sql.append(this.buildTransStepCondition(templateIdTransformation, stepIdDiffer));

        return sql.toString();
    }

    private String buildTransStepCondition(Long templateIdTransformation, Long stepIdDiffer) {
        StringBuilder sql = new StringBuilder();
        // 查 r_trans_step_condition
        List<TransStepConditionDO> transStepConditionDOS = transformationService.listTransStepCondition(templateIdTransformation);
        if (!CollectionUtils.isEmpty(transStepConditionDOS)) {
            List<Long> idConditions = transStepConditionDOS.stream().map(TransStepConditionDO::getIdCondition).collect(Collectors.toList());
            // 构建r_condition
            sql.append(this.buildCondition(idConditions));
        }

        for (TransStepConditionDO t : transStepConditionDOS) {
            TransStepConditionDO transStepConditionDO = BeanCopyUtils.copyProperties(t, TransStepConditionDO.class);
            transStepConditionDO.setIdStep(transStepConditionDO.getIdStep() + stepIdDiffer);
            sql.append(MysqlBuilderUtils.buildTransStepCondition(transStepConditionDO));
        }
        return sql.toString();
    }

    private String buildCondition(List<Long> idConditions) {
        StringBuilder sql = new StringBuilder();
        // 查r_condition
        List<ConditionDO> conditionDOS = transformationService.listCondition(idConditions);
        String joinIdConditions = idConditions.stream().map(String::valueOf).collect(Collectors.joining(","));
        sql.append(MysqlBuilderUtils.buildConditionDeleteIn(joinIdConditions));
        for (ConditionDO conditionDO : conditionDOS) {
            sql.append(MysqlBuilderUtils.buildCondition(conditionDO));
        }
        return sql.toString();
    }

    private String buildTransHop(Long templateIdTransformation, Long stepIdDiffer) {
        StringBuilder sql = new StringBuilder();
        // 查 r_trans_hop
        List<TransHopDO> transHopDOS = transformationService.listTransHop(templateIdTransformation);

        for (TransHopDO t : transHopDOS) {
            TransHopDO transHopDO = BeanCopyUtils.copyProperties(t, TransHopDO.class);
            transHopDO.setIdTransHop(KettleEtlUtils.getIdMaxTransRelated().addAndGetIdTransHopMax());
            transHopDO.setIdTransformation(KettleEtlUtils.getIdMaxTransRelated().getIdTransformationMax());
            transHopDO.setIdStepFrom(transHopDO.getIdStepFrom() + stepIdDiffer);
            transHopDO.setIdStepTo(transHopDO.getIdStepTo() + stepIdDiffer);
            sql.append(MysqlBuilderUtils.buildTransHop(transHopDO));
        }
        return sql.toString();
    }

    /**
     * 构建转换步骤数据源
     * @param templateIdTransformation 模板转换ID
     * @param stepIdDiffer ID差值
     * @return String
     */
    public String buildStepDatabase(Long templateIdTransformation, Long stepIdDiffer) {
        StringBuilder sql = new StringBuilder();
        // 查 r_step_database
        List<StepDatabaseDO> stepDatabaseDOS = transformationService.listStepDatabase(templateIdTransformation);

        for (StepDatabaseDO t : stepDatabaseDOS) {
            StepDatabaseDO buildDO = BeanCopyUtils.copyProperties(t, StepDatabaseDO.class);
            buildDO.setIdTransformation(KettleEtlUtils.getIdMaxTransRelated().getIdTransformationMax());
            buildDO.setIdStep(buildDO.getIdStep() + stepIdDiffer);
            // bond_ext_etl、etl_sync 不需要修改
            if (t.getIdDatabase() != ETL_SYNC_ID_DATABASE && t.getIdDatabase() != BOND_EXT_ETL_ID_DATABASE) {
                buildDO.setIdDatabase(KettleEtlUtils.getIdConnection());
            }
            sql.append(MysqlBuilderUtils.buildStepDatabase(buildDO));
        }
        return sql.toString();

    }

    /**
     * 构建步骤属性
     * @param stepDO 数据实体
     * @return String
     * @throws IOException IO异常
     */
    public String buildStepAttribute(StepDO stepDO) throws IOException {
        List<StepAttributeDO> stepAttributeDOS = transformationService.listStepAttribute(stepDO.getIdStep());
        if (stepDO.getName().startsWith(STEP_NAME_TABLE_INPUT)) {
            return this.template.processTableInput(stepAttributeDOS);
        }

        if (stepDO.getName().startsWith(STEP_NAME_QUERY_FROM_TABLE_NAME)) {
            return this.template.processQueryFromTableName(stepAttributeDOS);
        }

        if (STEP_NAME_INSERT_OR_UPDATE.equals(stepDO.getName())) {
            return this.processInsertOrUpdate(stepAttributeDOS);
        }

        if (stepDO.getName().startsWith(STEP_NAME_TABLE_OUT)) {
            return this.processTableOut(stepAttributeDOS);
        }

        if (stepDO.getName().startsWith(STEP_NAME_BATCH_INSERT) || stepDO.getName().startsWith(STEP_NAME_BATCH_UPDATE)) {
            return this.template.processBatchInsertOrUpdate(stepAttributeDOS);
        }

        StringBuilder sql = new StringBuilder();
        IdMaxTransRelatedBO idMaxTransRelatedBO = KettleEtlUtils.getIdMaxTransRelated();
        for (StepAttributeDO t : stepAttributeDOS) {
            StepAttributeDO buildDO = BeanCopyUtils.copyProperties(t, StepAttributeDO.class);
            buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
            buildDO.setIdTransformation(idMaxTransRelatedBO.getIdTransformationMax());
            buildDO.setIdStep(idMaxTransRelatedBO.getIdStepMax());
            // 构建 r_step_attribute
            sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
        }
        return sql.toString();
    }

    private String processTableOut(List<StepAttributeDO> stepAttributeDOS) {
        IdMaxTransRelatedBO idMaxTransRelatedBO = KettleEtlUtils.getIdMaxTransRelated();
        StringBuilder sql = new StringBuilder();
        for (StepAttributeDO t : stepAttributeDOS) {
            StepAttributeDO buildDO = BeanCopyUtils.copyProperties(t, StepAttributeDO.class);
            buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
            buildDO.setIdTransformation(idMaxTransRelatedBO.getIdTransformationMax());
            buildDO.setIdStep(idMaxTransRelatedBO.getIdStepMax());
            switch (t.getCode()) {
                case STEP_ATTRIBUTE_ID_CONNECTION:
                    sql.append(buildIfChangeOutDatabase(buildDO));
                    break;
                case STEP_ATTRIBUTE_CODE_COLUMN_NAME:
                case STEP_ATTRIBUTE_CODE_STREAM_NAME:
                    break;
                default:
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
            }
        }
        sql.append(processColumnAndStreamName(idMaxTransRelatedBO));
        return sql.toString();
    }

    /**
     * 处理步骤 insert_or_update
     * @param stepAttributeDOS 数据实体集合
     * @return String
     */
    public String processInsertOrUpdate(List<StepAttributeDO> stepAttributeDOS) {
        IdMaxTransRelatedBO idMaxTransRelatedBO = KettleEtlUtils.getIdMaxTransRelated();
        StringBuilder sql = new StringBuilder();
        for (StepAttributeDO t : stepAttributeDOS) {
            StepAttributeDO buildDO = BeanCopyUtils.copyProperties(t, StepAttributeDO.class);
            buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
            buildDO.setIdTransformation(idMaxTransRelatedBO.getIdTransformationMax());
            buildDO.setIdStep(idMaxTransRelatedBO.getIdStepMax());
            switch (t.getCode()) {
                case STEP_ATTRIBUTE_ID_CONNECTION:
                    sql.append(buildIfChangeOutDatabase(buildDO));
                    break;
                case STEP_ATTRIBUTE_KEY_FIELD:
                    sql.append(modKeyField(buildDO));
                    break;
                case STEP_ATTRIBUTE_CODE_VALUE_NAME:
                case STEP_ATTRIBUTE_CODE_VALUE_RENAME:
                case STEP_ATTRIBUTE_CODE_VALUE_UPDATE:
                    break;
                default:
                    sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
            }
        }
        List<ColumnInfoBO> columnInfoBOList = KettleEtlUtils.getSourceColumnList();
        List<String> columnNames = columnInfoBOList.stream().map(ColumnInfoBO::getColumnName).collect(Collectors.toList());
        columnNames.addAll(KettleEtlUtils.getColumnsFormat().keySet());
        sql.append(buildOutColumnMapping(idMaxTransRelatedBO, columnNames, STEP_ATTRIBUTE_CODE_VALUE_RENAME, STEP_ATTRIBUTE_CODE_VALUE_NAME));
        for (int i = 0; i < columnNames.size(); i++) {
            String flag = "id".equals(columnNames.get(i)) ? "N" : "Y";
            StepAttributeDO buildDO = createBuildStepAttributeDO(idMaxTransRelatedBO, i, STEP_ATTRIBUTE_CODE_VALUE_UPDATE, flag);
            // 构建 r_step_attribute
            sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));
        }
        return sql.toString();
    }

    private String modKeyField(StepAttributeDO buildDO) {
        for (Map.Entry<String, String> entry : KettleEtlUtils.getAliasMapping().entrySet()) {
            if ("id".equals(entry.getValue())) {
                buildDO.setValueStr(entry.getKey());
            }
        }
        return MysqlBuilderUtils.buildStepAttribute(buildDO);
    }

    private StepAttributeDO createBuildStepAttributeDO(IdMaxTransRelatedBO idMaxTransRelatedBO,
                                                       long i,
                                                       String code,
                                                       String valueStr) {
        StepAttributeDO buildDO = new StepAttributeDO();
        buildDO.setIdStepAttribute(idMaxTransRelatedBO.addAndGetIdStepAttributeMax());
        buildDO.setIdTransformation(idMaxTransRelatedBO.getIdTransformationMax());
        buildDO.setIdStep(idMaxTransRelatedBO.getIdStepMax());
        buildDO.setNr(i);
        buildDO.setCode(code);
        buildDO.setValueNum((long) 0);
        buildDO.setValueStr(valueStr);
        return buildDO;
    }

    /**
     * 按需要是否切换卖出库
     * @param stepAttributeDO 数据实体
     * @return String
     */
    public String buildIfChangeOutDatabase(StepAttributeDO stepAttributeDO) {
        if (StringUtils.isEmpty(kettleEtlConfig.getOutDatabase())) {
            return MysqlBuilderUtils.buildStepAttribute(stepAttributeDO);
        }
        stepAttributeDO.setValueNum(databaseCacheService.getDatabaseConfig().get(kettleEtlConfig.getOutDatabase()));
        return MysqlBuilderUtils.buildStepAttribute(stepAttributeDO);
    }

    /**
     * 处理目标库-源库的字段映射
     * @param idMaxTransRelatedBO 当前ID最大值
     * @return String
     */
    public String processColumnAndStreamName(IdMaxTransRelatedBO idMaxTransRelatedBO) {
        List<ColumnInfoBO> columnInfoBOList = KettleEtlUtils.getSourceColumnList();
        List<String> columnNames = columnInfoBOList.stream().map(ColumnInfoBO::getColumnName).collect(Collectors.toList());
        columnNames.addAll(KettleEtlUtils.getColumnsFormat().keySet());
        return buildOutColumnMapping(idMaxTransRelatedBO, columnNames, STEP_ATTRIBUTE_CODE_STREAM_NAME, STEP_ATTRIBUTE_CODE_COLUMN_NAME);
    }

    private String buildOutColumnMapping(IdMaxTransRelatedBO idMaxTransRelatedBO, List<String> columnNames, String code1, String code2) {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < columnNames.size(); i++) {
            Map<String, String> aliasMapping = KettleEtlUtils.getAliasMapping();
            String columnName = columnNames.get(i);
            String sourceColumn = columnName;
            if(ID.equals(aliasMapping.get(columnName)) || UPDATE_TIME.equals(aliasMapping.get(columnName))){
                sourceColumn = aliasMapping.get(columnName);
            }
            StepAttributeDO buildDO = createBuildStepAttributeDO(idMaxTransRelatedBO, i, code1, sourceColumn);
            sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO));

            String targetColumn = Objects.isNull(aliasMapping.get(columnName)) ? columnName : aliasMapping.get(columnName);
            StepAttributeDO buildDO2 = createBuildStepAttributeDO(idMaxTransRelatedBO, i, code2, targetColumn);
            sql.append(MysqlBuilderUtils.buildStepAttribute(buildDO2));
        }
        return sql.toString();
    }

    @Override
    public void buildJob() {
        JobPointBO jobPointBO = kettleEtlPointService.getJobPoint(this.template.getTemplateInfo().getId());
        String jobTemplateName = this.template.getTemplateInfo().getName();
        StringBuilder job = new StringBuilder();

        for (JobDO jobDO : jobPointBO.getJob()) {
            String name = jobDO.getName();
            if (name.contains(jobTemplateName)) {
                name = name.replace(jobTemplateName, KettleEtlUtils.getSourceTableName());
            }
            // 构建 r_job
            JobDO buildDO = BeanCopyUtils.copyProperties(jobDO, JobDO.class);
            buildDO.setIdJob(KettleEtlUtils.getIdMaxJobRelated().addAndGetIdJobMax());
            buildDO.setIdDirectory(KettleEtlUtils.getIdDirectory().intValue());
            buildDO.setName(name);
            job.append(MysqlBuilderUtils.buildJob(buildDO));

            job.append(this.buildJobAttribute(jobDO.getIdJob()));
            job.append(this.buildJobEntryRelation(jobDO.getIdJob()));
        }
        product.setJob(job.toString());
    }

    private String buildJobAttribute(Long templateIdJob) {
        StringBuilder sql = new StringBuilder();
        // 查 r_job_attribute
        List<JobAttributeDO> jobAttributeDOS = jobService.listJobAttribute(templateIdJob);

        for (JobAttributeDO t : jobAttributeDOS) {
            JobAttributeDO jobAttributeDO = BeanCopyUtils.copyProperties(t, JobAttributeDO.class);
            jobAttributeDO.setIdJobAttribute(KettleEtlUtils.getIdMaxJobRelated().addAndGetIdJobAttributeMax());
            jobAttributeDO.setIdJob(KettleEtlUtils.getIdMaxJobRelated().getIdJobMax());
            sql.append(MysqlBuilderUtils.buildJobAttribute(jobAttributeDO));
        }
        return sql.toString();
    }

    /**
     * 构建作业相关
     * @param idJob 作业ID
     * @return String
     */
    public String buildJobEntryRelation(Long idJob) {
        IdMaxJobRelatedBO idMaxJobRelatedBO = KettleEtlUtils.getIdMaxJobRelated();
        String jobTemplateName = this.template.getTemplateInfo().getName();
        StringBuilder sql = new StringBuilder();
        List<JobentryDO> jobEntryDOS = jobService.listJobEntry(idJob);
        List<JobentryCopyDO> jobEntryCopyDOS = jobService.queryJobEntryCopy(idJob);

        // 计算IdJobEntryCopyMax与作业模板的idJobEntry的差值
        long idJobMin = jobEntryCopyDOS.get(0).getIdJobentryCopy();
        Long idJobEntryCopyDiffer = idMaxJobRelatedBO.getIdJobEntryCopyMax() - idJobMin + 1L;

        for (JobentryDO jobentryDO : jobEntryDOS) {
            JobentryDO buildDO = BeanCopyUtils.copyProperties(jobentryDO, JobentryDO.class);
            buildDO.setIdJobentry(idMaxJobRelatedBO.addAndGetIdJobEntryMax());
            buildDO.setIdJob(idMaxJobRelatedBO.getIdJobMax());
            String name = jobentryDO.getName();
            if (name.contains(jobTemplateName)) {
                name = name.replace(jobTemplateName, KettleEtlUtils.getSourceTableName());
            }
            buildDO.setName(name);

            // 构建 r_jobentry
            sql.append(MysqlBuilderUtils.buildJobEntry(buildDO));

            // 构建 r_jobentry_attribute
            sql.append(this.buildJobEntryAttribute(jobentryDO.getIdJobentry()));
            sql.append(this.buildJobEntryCopy(jobentryDO.getIdJobentry(), idMaxJobRelatedBO));
            sql.append(this.buildJobEntryDatabase(jobentryDO.getIdJobentry(), idMaxJobRelatedBO));
        }
        // 构建 r_job_hop
        sql.append(this.buildJobHop(idJob, idMaxJobRelatedBO, idJobEntryCopyDiffer));

        // 构建 r_job_note
        sql.append(this.buildJobNote(idJob, idMaxJobRelatedBO));

        return sql.toString();
    }

    private String buildJobEntryCopy(Long idJobentry, IdMaxJobRelatedBO idMaxJobRelatedBO) {
        StringBuilder sql = new StringBuilder();
        // 查 r_jobentry_copy
        List<JobentryCopyDO> jobentryCopyDOS = jobService.listJobEntryCopy(idJobentry);

        for (JobentryCopyDO t : jobentryCopyDOS) {
            JobentryCopyDO jobentryCopyDO = BeanCopyUtils.copyProperties(t, JobentryCopyDO.class);
            jobentryCopyDO.setIdJobentryCopy(idMaxJobRelatedBO.addAndGetIdJobEntryCopyMax());
            jobentryCopyDO.setIdJobentry(idMaxJobRelatedBO.getIdJobEntryMax());
            jobentryCopyDO.setIdJob(idMaxJobRelatedBO.getIdJobMax());
            sql.append(MysqlBuilderUtils.buildJobEntryCopy(jobentryCopyDO));
        }
        return sql.toString();
    }

    /**
     * 构建作业数据源
     * @param idJobentry ID
     * @param idMaxJobRelatedBO 当前ID最大值
     * @return String
     */
    public String buildJobEntryDatabase(Long idJobentry, IdMaxJobRelatedBO idMaxJobRelatedBO) {
        StringBuilder sql = new StringBuilder();
        // 查 r_jobentry_database
        List<JobentryDatabaseDO> jobentryDatabaseDOS = jobService.listJobEntryDatabase(idJobentry);

        // 组装脚本
        for (JobentryDatabaseDO t : jobentryDatabaseDOS) {
            JobentryDatabaseDO jobentryDatabaseDO = BeanCopyUtils.copyProperties(t, JobentryDatabaseDO.class);
            jobentryDatabaseDO.setIdJob(idMaxJobRelatedBO.getIdJobMax().intValue());
            jobentryDatabaseDO.setIdJobentry(idMaxJobRelatedBO.getIdJobEntryMax());
            sql.append(MysqlBuilderUtils.buildJobEntryDatabase(jobentryDatabaseDO));
        }
        return sql.toString();
    }

    /**
     * 构建作业连接
     * @param idJob 作业ID
     * @param idMaxJobRelatedBO 当前ID最大值
     * @param differ ID差值
     * @return String
     */
    public String buildJobHop(Long idJob, IdMaxJobRelatedBO idMaxJobRelatedBO, Long differ) {
        StringBuilder sql = new StringBuilder();
        // 查r_job_hop
        List<JobHopDO> jobHopDOS = jobService.listJobHop(idJob);
        for (JobHopDO t : jobHopDOS) {
            JobHopDO jobHopDO = BeanCopyUtils.copyProperties(t, JobHopDO.class);
            jobHopDO.setIdJobHop(idMaxJobRelatedBO.addAndGetIdJobHopMax());
            jobHopDO.setIdJob(idMaxJobRelatedBO.getIdJobMax());
            jobHopDO.setIdJobentryCopyFrom((int) (jobHopDO.getIdJobentryCopyFrom() + differ));
            jobHopDO.setIdJobentryCopyTo((int) (jobHopDO.getIdJobentryCopyTo() + differ));
            sql.append(MysqlBuilderUtils.buildJobHop(jobHopDO));
        }
        return sql.toString();
    }

    /**
     * 构建作业Note
     * @param idJob 作业ID
     * @param idMaxJobRelatedBO 当前ID最大值
     * @return String
     */
    public String buildJobNote(Long idJob, IdMaxJobRelatedBO idMaxJobRelatedBO) {
        StringBuilder sql = new StringBuilder();
        // 查中间库 r_job_note
        List<JobNoteDO> jobNoteDOS = jobService.listJobNote(idJob);

        // 组装脚本
        for (JobNoteDO t : jobNoteDOS) {
            JobNoteDO jobNoteDO = BeanCopyUtils.copyProperties(t, JobNoteDO.class);
            jobNoteDO.setIdJob(idMaxJobRelatedBO.getIdJobMax());
            sql.append(MysqlBuilderUtils.buildJobNote(jobNoteDO));
        }
        return sql.toString();
    }

    /**
     * 构建 jobentry_attribute
     * @param idJobentry ID
     * @return String
     */
    public String buildJobEntryAttribute(Long idJobentry) {
        StringBuilder sql = new StringBuilder();
        IdMaxJobRelatedBO idMaxJobRelatedBO = KettleEtlUtils.getIdMaxJobRelated();
        String transTemplateName = this.template.getTemplateInfo().getName();
        List<JobentryAttributeDO> jobEntryAttributeDOS = jobService.listJobEntryAttribute(idJobentry);
        // 组装脚本
        for (JobentryAttributeDO t : jobEntryAttributeDOS) {
            JobentryAttributeDO jobentryAttributeDO = BeanCopyUtils.copyProperties(t, JobentryAttributeDO.class);
            jobentryAttributeDO.setIdJobentryAttribute(idMaxJobRelatedBO.addAndGetIdJobEntryAttributeMax());
            jobentryAttributeDO.setIdJob(idMaxJobRelatedBO.getIdJobMax());
            jobentryAttributeDO.setIdJobentry(idMaxJobRelatedBO.getIdJobEntryMax());
            String valueStr = t.getValueStr();
            if (JOBENTRY_ATTRIBUTE_CODE_NAME.equals(t.getCode()) && t.getValueStr().contains(transTemplateName)) {
                valueStr = valueStr.replace(transTemplateName, KettleEtlUtils.getSourceTableName());
            }
            jobentryAttributeDO.setValueStr(valueStr);
            sql.append(MysqlBuilderUtils.buildJobEntryAttribute(jobentryAttributeDO));
        }
        return sql.toString();
    }

    private void addUpdateTimeId(List<ColumnInfoBO> needColumns) {
        ColumnInfoBO updateTimeId = new ColumnInfoBO();
        updateTimeId.setPk(false);
        updateTimeId.setColumnName("update_time_id");
        updateTimeId.setColumnType("bigint(20)");
        updateTimeId.setColumnComment("更新时间id");
        updateTimeId.setDataType("bigint");
        needColumns.add(updateTimeId);
    }

    @Override
    public KettleEtlProductBO build() {
        return this.product;
    }
}

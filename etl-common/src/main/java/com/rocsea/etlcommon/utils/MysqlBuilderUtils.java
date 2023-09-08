package com.rocsea.etlcommon.utils;

import com.rocsea.etlcommon.model.bo.kettlepdi.KettlePackBO;
import com.rocsea.etlcommon.model.bo.kettlepdi.KettleTaskConfigBO;
import com.rocsea.etlcommon.model.entity.etlsync.DmlSubscribeConfigDO;
import com.rocsea.etlcommon.model.entity.etlsync.EtlSyncCheckConfigDO;
import com.rocsea.etlcommon.model.entity.kettleresource.*;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.rocsea.etlcommon.model.constant.KettleEtlConstant.DATE_FORMAT_SEC;
import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.COMMA;
import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.LINE_FEED;
import static com.rocsea.etlcommon.model.constant.MysqlScriptConstant.SINGLE_QUOTE;

/**
 * @Author RocSea
 * @Date 2023/1/12
 */
public final class MysqlBuilderUtils {
    private static final String VALUES_PREFIX = "VALUES (";
    private static final String VALUES_SUFFIX = ");";
    private static final String INSERT_DATABASE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_database`(`ID_DATABASE`, `NAME`, `ID_DATABASE_TYPE`, `ID_DATABASE_CONTYPE`, `HOST_NAME`" +
                    ", `DATABASE_NAME`, `PORT`, `USERNAME`, `PASSWORD`, `SERVERNAME`, `DATA_TBS`, `INDEX_TBS`) ";

    private static final String INSERT_DATABASE_ATTRIBUTE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_database_attribute`(`ID_DATABASE_ATTRIBUTE`, `ID_DATABASE`, `CODE`, `VALUE_STR`) ";

    private static final String INSERT_DIRECTORY_PREFIX =
            "INSERT INTO `kettle_resource`.`r_directory`(`ID_DIRECTORY`, `ID_DIRECTORY_PARENT`, `DIRECTORY_NAME`) ";

    private static final String INSERT_TRANSFORMATION_PREFIX =
            "INSERT INTO `kettle_resource`.`r_transformation`(`ID_TRANSFORMATION`, `ID_DIRECTORY`, `NAME`, `DESCRIPTION`" +
                    ", `EXTENDED_DESCRIPTION`, `TRANS_VERSION`, `TRANS_STATUS`, `ID_STEP_READ`, `ID_STEP_WRITE`, `ID_STEP_INPUT`" +
                    ", `ID_STEP_OUTPUT`, `ID_STEP_UPDATE`, `ID_DATABASE_LOG`, `TABLE_NAME_LOG`, `USE_BATCHID`, `USE_LOGFIELD`, " +
                    "`ID_DATABASE_MAXDATE`, `TABLE_NAME_MAXDATE`, `FIELD_NAME_MAXDATE`, `OFFSET_MAXDATE`, `DIFF_MAXDATE`, `CREATED_USER`" +
                    ", `CREATED_DATE`, `MODIFIED_USER`, `MODIFIED_DATE`, `SIZE_ROWSET`) ";

    private static final String INSERT_TRANS_ATTRIBUTE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_trans_attribute`(`ID_TRANS_ATTRIBUTE`, `ID_TRANSFORMATION`, `NR`, `CODE`, `VALUE_NUM`" +
                    ", `VALUE_STR`) ";

    private static final String INSERT_STEP_PREFIX =
            "INSERT INTO `kettle_resource`.`r_step`(`ID_STEP`, `ID_TRANSFORMATION`, `NAME`, `DESCRIPTION`, `ID_STEP_TYPE`, `DISTRIBUTE`" +
                    ", `COPIES`, `GUI_LOCATION_X`, `GUI_LOCATION_Y`, `GUI_DRAW`, `COPIES_STRING`) ";

    private static final String INSERT_STEP_ATTRIBUTE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_step_attribute`(`ID_STEP_ATTRIBUTE`, `ID_TRANSFORMATION`, `ID_STEP`, `NR`, `CODE`" +
                    ", `VALUE_NUM`, `VALUE_STR`) ";

    private static final String INSERT_STEP_DATABASE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_step_database`(`ID_TRANSFORMATION`, `ID_STEP`, `ID_DATABASE`) ";

    public static final String INSERT_TRANS_HOP_PREFIX =
            "INSERT INTO `kettle_resource`.`r_trans_hop`(`ID_TRANS_HOP`, `ID_TRANSFORMATION`, `ID_STEP_FROM`, `ID_STEP_TO`, `ENABLED`) ";

    private static final String DELETE_CONDITION_PREFIX = "DELETE FROM r_condition WHERE ID_CONDITION IN(";

    private static final String INSERT_CONDITION_PREFIX =
            "INSERT INTO `kettle_resource`.`r_condition`(`ID_CONDITION`, `ID_CONDITION_PARENT`, `NEGATED`, `OPERATOR`, `LEFT_NAME`" +
                    ", `CONDITION_FUNCTION`, `RIGHT_NAME`, `ID_VALUE_RIGHT`) ";

    private static final String INSERT_TRANS_STEP_CONDITION_PREFIX =
            "INSERT INTO `kettle_resource`.`r_trans_step_condition`(`ID_TRANSFORMATION`, `ID_STEP`, `ID_CONDITION`) ";

    private static final String INSERT_JOB_PREFIX =
            "INSERT INTO `kettle_resource`.`r_job`(`ID_JOB`, `ID_DIRECTORY`, `NAME`, `DESCRIPTION`, `EXTENDED_DESCRIPTION`, `JOB_VERSION`" +
                    ", `JOB_STATUS`, `ID_DATABASE_LOG`, `TABLE_NAME_LOG`, `CREATED_USER`, `CREATED_DATE`, `MODIFIED_USER`" +
                    ", `MODIFIED_DATE`, `USE_BATCH_ID`, `PASS_BATCH_ID`, `USE_LOGFIELD`, `SHARED_FILE`) ";

    private static final String INSERT_JOB_ATTRIBUTE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_job_attribute`(`ID_JOB_ATTRIBUTE`, `ID_JOB`, `NR`, `CODE`, `VALUE_NUM`, `VALUE_STR`) ";

    private static final String INSERT_JOBENTRY_PREFIX =
            "INSERT INTO `kettle_resource`.`r_jobentry`(`ID_JOBENTRY`, `ID_JOB`, `ID_JOBENTRY_TYPE`, `NAME`, `DESCRIPTION`) ";

    private static final String INSERT_JOBENTRY_ATTRIBUTE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_jobentry_attribute`(`ID_JOBENTRY_ATTRIBUTE`, `ID_JOB`, `ID_JOBENTRY`, `NR`, `CODE`" +
                    ", `VALUE_NUM`, `VALUE_STR`) ";

    private static final String INSERT_JOBENTRY_COPY_PREFIX =
            "INSERT INTO `kettle_resource`.`r_jobentry_copy`(`ID_JOBENTRY_COPY`, `ID_JOBENTRY`, `ID_JOB`, `ID_JOBENTRY_TYPE`, `NR`" +
                    ", `GUI_LOCATION_X`, `GUI_LOCATION_Y`, `GUI_DRAW`, `PARALLEL`) ";

    private static final String INSERT_JOBENTRY_DATABASE_PREFIX =
            "INSERT INTO `kettle_resource`.`r_jobentry_database`(`ID_JOB`, `ID_JOBENTRY`, `ID_DATABASE`) ";

    private static final String INSERT_JOB_HOP_PREFIX =
            "INSERT INTO `kettle_resource`.`r_job_hop`(`ID_JOB_HOP`, `ID_JOB`, `ID_JOBENTRY_COPY_FROM`, `ID_JOBENTRY_COPY_TO`, `ENABLED`" +
                    ", `EVALUATION`, `UNCONDITIONAL`) ";

    private static final String INSERT_JOB_NOTE_PREFIX = "INSERT INTO `kettle_resource`.`r_job_note`(`ID_JOB`, `ID_NOTE`) ";

    private static final String INSERT_KP_JOB_PREFIX =
            "INSERT INTO `kettle-pack`.`kp_job`(`ID`, `CATEGORY_ID`, `JOB_NAME`, `JOB_DESCRIPTION`, `JOB_TYPE`, `JOB_PATH`" +
                    ", `JOB_REPOSITORY_ID`, `JOB_QUARTZ`, `SYNC_STRATEGY`, `JOB_LOG_LEVEL`, `JOB_STATUS`, `CREATE_TIME`, `CREATE_BY`" +
                    ", `UPDATE_TIME`, `UPDATE_BY`, `DEL_FLAG`, `JOB_PARAMS`, `MONITOR_SUCCESS`, `MONITOR_FAIL`, `LAST_EXECUTE_TIME`" +
                    ", `NEXT_EXECUTE_TIME`, `ERROR_ALARM_FLAG`, `ERROR_ALARM_MAILS`, `RUN_CONFIG`, `REMOTE_SERVER`) ";

    private static final String INSERT_KETTLE_TASK_CONFIG_PREFIX =
            "INSERT INTO `etl_sync`.`kettle_task_config`(`next_insert_id`, `next_update_id`, `next_start_time`, `next_end_time`" +
                    ", `sync_status`, `task_name`, `task_type`, `compensation_type`) ";

    private static final String DELETE_JOB_PREFIX = "DELETE FROM `kettle_resource`.`r_job` WHERE ID_JOB IN (";
    private static final String DELETE_JOB_ATTRIBUTE_PREFIX = "DELETE FROM `kettle_resource`.`r_job_attribute` WHERE ID_JOB IN (";
    private static final String DELETE_JOB_HOP_PREFIX = "DELETE FROM `kettle_resource`.`r_job_hop` WHERE ID_JOB IN (";
    private static final String DELETE_JOB_LOCK_PREFIX = "DELETE FROM `kettle_resource`.`r_job_lock` WHERE ID_JOB IN (";
    private static final String DELETE_JOBENTRY_PREFIX = "DELETE FROM `kettle_resource`.`r_jobentry` WHERE ID_JOB IN (";
    private static final String DELETE_JOBENTRY_ATTRIBUTE_PREFIX = "DELETE FROM `kettle_resource`.`r_jobentry_attribute` WHERE ID_JOB IN (";
    private static final String DELETE_JOBENTRY_COPY_PREFIX = "DELETE FROM `kettle_resource`.`r_jobentry_copy` WHERE ID_JOB IN (";
    private static final String DELETE_JOB_NOTE_PREFIX = "DELETE FROM `kettle_resource`.`r_job_note` WHERE ID_JOB IN (";
    private static final String DELETE_JOBENTRY_DATABASE_PREFIX = "DELETE FROM `kettle_resource`.`r_jobentry_database` WHERE ID_JOB IN (";


    private static final String DELETE_TRANSFORMATION_PREFIX = "DELETE FROM `kettle_resource`.`r_transformation` WHERE ID_TRANSFORMATION IN (";
    private static final String DELETE_TRANS_ATTRIBUTE_PREFIX = "DELETE FROM `kettle_resource`.`r_trans_attribute` WHERE ID_TRANSFORMATION IN (";
    private static final String DELETE_STEP_PREFIX = "DELETE FROM `kettle_resource`.`r_step` WHERE ID_TRANSFORMATION IN (";
    private static final String DELETE_STEP_ATTRIBUTE_PREFIX = "DELETE FROM `kettle_resource`.`r_step_attribute` WHERE ID_TRANSFORMATION IN (";
    private static final String DELETE_STEP_DATABASE_PREFIX = "DELETE FROM `kettle_resource`.`r_step_database` WHERE ID_TRANSFORMATION IN (";
    private static final String DELETE_TRANS_HOP_PREFIX = "DELETE FROM `kettle_resource`.`r_trans_hop` WHERE ID_TRANSFORMATION IN (";
    private static final String DELETE_TRANS_STEP_CONDITION_PREFIX = "DELETE FROM `kettle_resource`.`r_trans_step_condition` WHERE ID_TRANSFORMATION IN (";

    private static final String SEMICOLON = ";";
    private static final String INSERT_ETL_SYNC_CHECK_CONFIG_PREFIX = "INSERT INTO `etl_sync`.`etl_sync_check_config` " +
            "(`source_service_module`, " +
            "`source_schema_name`, `source_table_name`, `source_join_condition`, `source_delete_type`, `source_where_condition`, " +
            "`source_table_type`, `target_service_module`, `target_schema_name`, `target_table_name`, `target_join_condition`, " +
            "`target_delete_type`, `target_where_condition`, `target_table_type`) ";

    private static final String INSERT_DML_SUBSCRIBE_CONFIG_PREFIX = "INSERT INTO `etl_sync`.`dml_subscribe_config` " +
            "(`target_service_module`, `source_table_topic`, `target_schema_name`, `target_table_name`, `operation_type`, " +
            "`enable_status`, `dml_consume_type`, `dml_source_sharding_field`)";
    private static final String UPDATE_STEP_ATTRIBUTE_PREFIX =
            "update `kettle_resource`.`r_step_attribute` set VALUE_NUM=";
    private static final String UPDATE_STEP_ATTRIBUTE_SUFFIX =
            " where ID_STEP_ATTRIBUTE=";

    private MysqlBuilderUtils() {
    }

    public static String buildDatabase(DatabaseDO databaseDO) {
        return INSERT_DATABASE_PREFIX + VALUES_PREFIX + databaseDO.getIdDatabase() +
                COMMA + SINGLE_QUOTE + databaseDO.getName() + SINGLE_QUOTE +
                COMMA + databaseDO.getIdDatabaseType() +
                COMMA + databaseDO.getIdDatabaseContype() +
                COMMA + SINGLE_QUOTE + databaseDO.getHostName() + SINGLE_QUOTE +
                COMMA + SINGLE_QUOTE + databaseDO.getDatabaseName() + SINGLE_QUOTE +
                COMMA + databaseDO.getPort() +
                COMMA + SINGLE_QUOTE + databaseDO.getUsername() + SINGLE_QUOTE +
                COMMA + SINGLE_QUOTE + databaseDO.getPassword() + SINGLE_QUOTE +
                COMMA + databaseDO.getServername() +
                COMMA + databaseDO.getDataTbs() +
                COMMA + databaseDO.getIndexTbs() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildDatabaseAttribute(DatabaseAttributeDO databaseAttributeDO) {
        String valueStr;
        if (databaseAttributeDO.getValueStr() != null) {
            valueStr = SINGLE_QUOTE + databaseAttributeDO.getValueStr() + SINGLE_QUOTE;
        } else {
            valueStr = databaseAttributeDO.getValueStr();
        }
        return INSERT_DATABASE_ATTRIBUTE_PREFIX + VALUES_PREFIX + databaseAttributeDO.getIdDatabaseAttribute() +
                COMMA + databaseAttributeDO.getIdDatabase() +
                COMMA + SINGLE_QUOTE + databaseAttributeDO.getCode() + SINGLE_QUOTE +
                COMMA + valueStr + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildDirectory(DirectoryDO directoryDO) {
        return INSERT_DIRECTORY_PREFIX + VALUES_PREFIX + directoryDO.getIdDirectory() +
                COMMA + directoryDO.getIdDirectoryParent() +
                COMMA + SINGLE_QUOTE + directoryDO.getDirectoryName() + SINGLE_QUOTE +
                VALUES_SUFFIX + LINE_FEED;

    }

    public static String buildTransformation(TransformationDO transformationDO) {
        return INSERT_TRANSFORMATION_PREFIX + VALUES_PREFIX + transformationDO.getIdTransformation() +
                COMMA + transformationDO.getIdDirectory() +
                StringFormatUtils.sqlValueFormat(transformationDO.getName()) +
                StringFormatUtils.sqlValueFormat(transformationDO.getDescription()) +
                StringFormatUtils.sqlValueFormat(transformationDO.getExtendedDescription()) +
                StringFormatUtils.sqlValueFormat(transformationDO.getTransVersion()) +
                COMMA + transformationDO.getTransStatus() +
                COMMA + transformationDO.getIdStepRead() +
                COMMA + transformationDO.getIdStepWrite() +
                COMMA + transformationDO.getIdStepInput() +
                COMMA + transformationDO.getIdStepOutput() +
                COMMA + transformationDO.getIdStepUpdate() +
                COMMA + transformationDO.getIdDatabaseLog() +
                StringFormatUtils.sqlValueFormat(transformationDO.getTableNameLog()) +
                COMMA + transformationDO.getUseBatchid() +
                COMMA + transformationDO.getUseLogfield() +
                COMMA + transformationDO.getIdDatabaseMaxdate() +
                StringFormatUtils.sqlValueFormat(transformationDO.getTableNameMaxdate()) +
                StringFormatUtils.sqlValueFormat(transformationDO.getFieldNameMaxdate()) +
                COMMA + transformationDO.getOffsetMaxdate() +
                COMMA + transformationDO.getDiffMaxdate() +
                StringFormatUtils.sqlValueFormat(transformationDO.getCreatedUser()) +
                COMMA + SINGLE_QUOTE + DateFormatUtils.format(new Date(), DATE_FORMAT_SEC) + SINGLE_QUOTE +
                StringFormatUtils.sqlValueFormat(transformationDO.getModifiedUser()) +
                COMMA + SINGLE_QUOTE + DateFormatUtils.format(new Date(), DATE_FORMAT_SEC) + SINGLE_QUOTE +
                COMMA + transformationDO.getSizeRowset() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildTransAttribute(TransAttributeDO transAttributeDO) {
        return INSERT_TRANS_ATTRIBUTE_PREFIX + VALUES_PREFIX + transAttributeDO.getIdTransAttribute() +
                COMMA + transAttributeDO.getIdTransformation() +
                COMMA + transAttributeDO.getNr() +
                StringFormatUtils.sqlValueFormat(transAttributeDO.getCode()) +
                COMMA + transAttributeDO.getValueNum() +
                StringFormatUtils.sqlValueFormat(transAttributeDO.getValueStr()) +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildStep(StepDO stepDO) {
        return INSERT_STEP_PREFIX + VALUES_PREFIX + stepDO.getIdStep() +
                COMMA + stepDO.getIdTransformation() +
                StringFormatUtils.sqlValueFormat(stepDO.getName()) +
                StringFormatUtils.sqlValueFormat(stepDO.getDescription()) +
                COMMA + stepDO.getIdStepType() +
                COMMA + stepDO.getDistribute() +
                COMMA + stepDO.getCopies() +
                COMMA + stepDO.getGuiLocationX() +
                COMMA + stepDO.getGuiLocationY() +
                COMMA + stepDO.getGuiDraw() +
                StringFormatUtils.sqlValueFormat(stepDO.getCopiesString()) +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildStepAttribute(StepAttributeDO stepAttributeDO) {
        return INSERT_STEP_ATTRIBUTE_PREFIX + VALUES_PREFIX + stepAttributeDO.getIdStepAttribute() +
                COMMA + stepAttributeDO.getIdTransformation() +
                COMMA + stepAttributeDO.getIdStep() +
                COMMA + stepAttributeDO.getNr() +
                StringFormatUtils.sqlValueFormat(stepAttributeDO.getCode()) +
                COMMA + stepAttributeDO.getValueNum() +
                StringFormatUtils.sqlValueFormat(StringFormatUtils.specialCharacterEscape(stepAttributeDO.getValueStr())) +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildStepDatabase(StepDatabaseDO stepDatabaseDO) {
        return INSERT_STEP_DATABASE_PREFIX + VALUES_PREFIX + stepDatabaseDO.getIdTransformation() +
                COMMA + stepDatabaseDO.getIdStep() +
                COMMA + stepDatabaseDO.getIdDatabase() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildTransHop(TransHopDO transHopDO) {
        return INSERT_TRANS_HOP_PREFIX + VALUES_PREFIX + transHopDO.getIdTransHop() +
                COMMA + transHopDO.getIdTransformation() +
                COMMA + transHopDO.getIdStepFrom() +
                COMMA + transHopDO.getIdStepTo() +
                COMMA + transHopDO.getEnabled() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildConditionDeleteIn(String joinIdConditions) {
        return DELETE_CONDITION_PREFIX + joinIdConditions + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildCondition(ConditionDO conditionDO) {
        return INSERT_CONDITION_PREFIX + VALUES_PREFIX + conditionDO.getIdCondition() +
                COMMA + conditionDO.getIdConditionParent() +
                COMMA + conditionDO.getNegated() +
                StringFormatUtils.sqlValueFormat(conditionDO.getOperator()) +
                StringFormatUtils.sqlValueFormat(conditionDO.getLeftName()) +
                StringFormatUtils.sqlValueFormat(conditionDO.getConditionFunction()) +
                StringFormatUtils.sqlValueFormat(conditionDO.getRightName()) +
                COMMA + conditionDO.getIdValueRight() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildTransStepCondition(TransStepConditionDO transStepConditionDO) {
        return INSERT_TRANS_STEP_CONDITION_PREFIX + VALUES_PREFIX + transStepConditionDO.getIdTransformation() +
                COMMA + transStepConditionDO.getIdStep() +
                COMMA + transStepConditionDO.getIdCondition() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJob(JobDO jobDO) {
        return INSERT_JOB_PREFIX + VALUES_PREFIX + jobDO.getIdJob() +
                COMMA + jobDO.getIdDirectory() +
                StringFormatUtils.sqlValueFormat(jobDO.getName()) +
                StringFormatUtils.sqlValueFormat(jobDO.getDescription()) +
                StringFormatUtils.sqlValueFormat(jobDO.getExtendedDescription()) +
                StringFormatUtils.sqlValueFormat(jobDO.getJobVersion()) +
                COMMA + jobDO.getJobStatus() +
                COMMA + jobDO.getIdDatabaseLog() +
                StringFormatUtils.sqlValueFormat(jobDO.getTableNameLog()) +
                StringFormatUtils.sqlValueFormat(jobDO.getCreatedUser()) +
                StringFormatUtils.sqlValueFormat(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")) +
                StringFormatUtils.sqlValueFormat(jobDO.getModifiedUser()) +
                StringFormatUtils.sqlValueFormat(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")) +
                COMMA + jobDO.getUseBatchId() +
                COMMA + jobDO.getPassBatchId() +
                COMMA + jobDO.getUseLogfield() +
                StringFormatUtils.sqlValueFormat(jobDO.getSharedFile()) +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobAttribute(JobAttributeDO jobAttributeDO) {
        return INSERT_JOB_ATTRIBUTE_PREFIX + VALUES_PREFIX + jobAttributeDO.getIdJobAttribute() +
                COMMA + jobAttributeDO.getIdJob() +
                COMMA + jobAttributeDO.getNr() +
                StringFormatUtils.sqlValueFormat(jobAttributeDO.getCode()) +
                COMMA + jobAttributeDO.getValueNum() +
                StringFormatUtils.sqlValueFormat(jobAttributeDO.getValueStr()) +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntry(JobentryDO jobentryDO) {
        return INSERT_JOBENTRY_PREFIX + VALUES_PREFIX + jobentryDO.getIdJobentry() +
                COMMA + jobentryDO.getIdJob() +
                COMMA + jobentryDO.getIdJobentryType() +
                StringFormatUtils.sqlValueFormat(jobentryDO.getName()) +
                StringFormatUtils.sqlValueFormat(jobentryDO.getDescription()) +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntryAttribute(JobentryAttributeDO jobentryAttributeDO) {
        return INSERT_JOBENTRY_ATTRIBUTE_PREFIX + VALUES_PREFIX + jobentryAttributeDO.getIdJobentryAttribute() +
                COMMA + jobentryAttributeDO.getIdJob() +
                COMMA + jobentryAttributeDO.getIdJobentry() +
                COMMA + jobentryAttributeDO.getNr() +
                StringFormatUtils.sqlValueFormat(jobentryAttributeDO.getCode()) +
                COMMA + jobentryAttributeDO.getValueNum() +
                StringFormatUtils.sqlValueFormat(jobentryAttributeDO.getValueStr()) +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntryCopy(JobentryCopyDO jobentryCopyDO) {
        return INSERT_JOBENTRY_COPY_PREFIX + VALUES_PREFIX + jobentryCopyDO.getIdJobentryCopy() +
                COMMA + jobentryCopyDO.getIdJobentry() +
                COMMA + jobentryCopyDO.getIdJob() +
                COMMA + jobentryCopyDO.getIdJobentryType() +
                COMMA + jobentryCopyDO.getNr() +
                COMMA + jobentryCopyDO.getGuiLocationX() +
                COMMA + jobentryCopyDO.getGuiLocationY() +
                COMMA + jobentryCopyDO.getGuiDraw() +
                COMMA + jobentryCopyDO.getParallel() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntryDatabase(JobentryDatabaseDO jobentryDatabaseDO) {
        return INSERT_JOBENTRY_DATABASE_PREFIX + VALUES_PREFIX + jobentryDatabaseDO.getIdJob() +
                COMMA + jobentryDatabaseDO.getIdJobentry() +
                COMMA + jobentryDatabaseDO.getIdDatabase() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobHop(JobHopDO jobHopDO) {
        return INSERT_JOB_HOP_PREFIX + VALUES_PREFIX + jobHopDO.getIdJobHop() +
                COMMA + jobHopDO.getIdJob() +
                COMMA + jobHopDO.getIdJobentryCopyFrom() +
                COMMA + jobHopDO.getIdJobentryCopyTo() +
                COMMA + jobHopDO.getEnabled() +
                COMMA + jobHopDO.getEvaluation() +
                COMMA + jobHopDO.getUnconditional() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobNote(JobNoteDO jobNoteDO) {
        return INSERT_JOB_NOTE_PREFIX + VALUES_PREFIX + jobNoteDO.getIdJob() +
                COMMA + jobNoteDO.getIdNote() +
                VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildKettlePack(KettlePackBO kettlePackBO) {
        return INSERT_KP_JOB_PREFIX + VALUES_PREFIX + SINGLE_QUOTE + kettlePackBO.getId() + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + kettlePackBO.getCategoryId() + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + kettlePackBO.getJobName() + SINGLE_QUOTE
                + COMMA + kettlePackBO.getJobDescription()
                + COMMA + SINGLE_QUOTE + kettlePackBO.getJobType() + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + kettlePackBO.getJobPath() + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + kettlePackBO.getJobRepositoryId() + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + kettlePackBO.getJobQuartz() + SINGLE_QUOTE
                + COMMA + kettlePackBO.getSyncStrategy()
                + COMMA + SINGLE_QUOTE + kettlePackBO.getJobLogLevel() + SINGLE_QUOTE
                + COMMA + kettlePackBO.getJobStatus()
                + COMMA + SINGLE_QUOTE + DateFormatUtils.format(kettlePackBO.getCreateTime(), DATE_FORMAT_SEC) + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + kettlePackBO.getCreateBy() + SINGLE_QUOTE
                + COMMA + kettlePackBO.getUpdateTime()
                + COMMA + kettlePackBO.getUpdateBy()
                + COMMA + kettlePackBO.getDelFlag()
                + COMMA + SINGLE_QUOTE + kettlePackBO.getJobParams() + SINGLE_QUOTE
                + COMMA + kettlePackBO.getMonitorSuccess()
                + COMMA + kettlePackBO.getMonitorFail()
                + COMMA + kettlePackBO.getLastExecuteTime()
                + COMMA + kettlePackBO.getNextExecuteTime()
                + COMMA + kettlePackBO.getErrorAlarmFlag()
                + COMMA + kettlePackBO.getErrorAlarmMails()
                + COMMA + SINGLE_QUOTE + kettlePackBO.getRunConfig() + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + kettlePackBO.getRemoteServer() + SINGLE_QUOTE + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildKettleTaskConfig(KettleTaskConfigBO kettleTaskConfigBO) {
        return INSERT_KETTLE_TASK_CONFIG_PREFIX + VALUES_PREFIX + kettleTaskConfigBO.getNextInsertId()
                + COMMA + kettleTaskConfigBO.getNextUpdateId()
                + COMMA + SINGLE_QUOTE + DateFormatUtils.format(kettleTaskConfigBO.getNextStartTime(), DATE_FORMAT_SEC) + SINGLE_QUOTE
                + COMMA + SINGLE_QUOTE + DateFormatUtils.format(kettleTaskConfigBO.getNextEndTime(), DATE_FORMAT_SEC) + SINGLE_QUOTE
                + COMMA + kettleTaskConfigBO.getSyncStatus()
                + COMMA + SINGLE_QUOTE + kettleTaskConfigBO.getTaskName() + SINGLE_QUOTE
                + COMMA + kettleTaskConfigBO.getTaskType()
                + COMMA + kettleTaskConfigBO.getCompensationType() + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobDeleteIn(String jobIds) {
        return DELETE_JOB_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobAttributeDeleteIn(String jobIds) {
        return DELETE_JOB_ATTRIBUTE_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobHopDeleteIn(String jobIds) {
        return DELETE_JOB_HOP_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobLockDeleteIn(String jobIds) {
        return DELETE_JOB_LOCK_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntryDeleteIn(String jobIds) {
        return DELETE_JOBENTRY_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntryAttributeDeleteIn(String jobIds) {
        return DELETE_JOBENTRY_ATTRIBUTE_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntryCopyDeleteIn(String jobIds) {
        return DELETE_JOBENTRY_COPY_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobNoteDeleteIn(String jobIds) {
        return DELETE_JOB_NOTE_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildJobEntryDatabaseDeleteIn(String jobIds) {
        return DELETE_JOBENTRY_DATABASE_PREFIX + jobIds + VALUES_SUFFIX + LINE_FEED;
    }


    public static String buildTransformationDeleteIn(String transformationIds) {
        return DELETE_TRANSFORMATION_PREFIX + transformationIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildTransAttributeDeleteIn(String transformationIds) {
        return DELETE_TRANS_ATTRIBUTE_PREFIX + transformationIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildStepDeleteIn(String transformationIds) {
        return DELETE_STEP_PREFIX + transformationIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildStepAttributeDeleteIn(String transformationIds) {
        return DELETE_STEP_ATTRIBUTE_PREFIX + transformationIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildStepDatabaseDeleteIn(String transformationIds) {
        return DELETE_STEP_DATABASE_PREFIX + transformationIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildTransHopDeleteIn(String transformationIds) {
        return DELETE_TRANS_HOP_PREFIX + transformationIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildTranStepConditionDeleteIn(String transformationIds) {
        return DELETE_TRANS_STEP_CONDITION_PREFIX + transformationIds + VALUES_SUFFIX + LINE_FEED;
    }

    public static String buildTransAttribute(List<TransAttributeDO> transAttributeDOs) {
        StringBuilder sql = new StringBuilder();
        for (TransAttributeDO transAttributeDO : transAttributeDOs) {
            sql.append(buildTransAttribute(transAttributeDO));
        }
        return sql.toString();
    }

    public static String buildStep(List<StepDO> stepDOs) {
        StringBuilder sql = new StringBuilder();
        for (StepDO stepDO : stepDOs) {
            sql.append(buildStep(stepDO));
        }
        return sql.toString();
    }

    public static String buildStepAttribute(List<StepAttributeDO> stepAttributeDOs) {
        StringBuilder sql = new StringBuilder();
        for (StepAttributeDO stepAttributeDO : stepAttributeDOs) {
            sql.append(buildStepAttribute(stepAttributeDO));
        }
        return sql.toString();
    }

    public static String buildStepDatabase(List<StepDatabaseDO> stepDatabaseDOs) {
        StringBuilder sql = new StringBuilder();
        for (StepDatabaseDO stepDatabaseDO : stepDatabaseDOs) {
            sql.append(buildStepDatabase(stepDatabaseDO));
        }
        return sql.toString();
    }

    public static String buildTransHop(List<TransHopDO> transHopDOs) {
        StringBuilder sql = new StringBuilder();
        for (TransHopDO transHopDO : transHopDOs) {
            sql.append(buildTransHop(transHopDO));
        }
        return sql.toString();
    }

    public static String buildTransStepCondition(List<TransStepConditionDO> transStepConditionDOs) {
        StringBuilder sql = new StringBuilder();
        for (TransStepConditionDO transStepConditionDO : transStepConditionDOs) {
            sql.append(buildTransStepCondition(transStepConditionDO));
        }
        return sql.toString();
    }

    public static String buildJobAttribute(List<JobAttributeDO> jobAttributeDOs) {
        StringBuilder sql = new StringBuilder();
        for (JobAttributeDO jobAttributeDO : jobAttributeDOs) {
            sql.append(buildJobAttribute(jobAttributeDO));
        }
        return sql.toString();
    }

    public static String buildJobHop(List<JobHopDO> jobHopDOs) {
        StringBuilder sql = new StringBuilder();
        for (JobHopDO jobHopDO : jobHopDOs) {
            sql.append(buildJobHop(jobHopDO));
        }
        return sql.toString();
    }

    public static String buildJobEntry(List<JobentryDO> jobentryDOs) {
        StringBuilder sql = new StringBuilder();
        for (JobentryDO jobentryDO : jobentryDOs) {
            sql.append(buildJobEntry(jobentryDO));
        }
        return sql.toString();
    }

    public static String buildJobEntryAttribute(List<JobentryAttributeDO> jobentryAttributeDOs) {
        StringBuilder sql = new StringBuilder();
        for (JobentryAttributeDO jobentryAttributeDO : jobentryAttributeDOs) {
            sql.append(buildJobEntryAttribute(jobentryAttributeDO));
        }
        return sql.toString();
    }

    public static String buildJobEntryCopy(List<JobentryCopyDO> jobentryCopyDOs) {
        StringBuilder sql = new StringBuilder();
        for (JobentryCopyDO jobentryCopyDO : jobentryCopyDOs) {
            sql.append(buildJobEntryCopy(jobentryCopyDO));
        }
        return sql.toString();
    }

    public static String buildJobNote(List<JobNoteDO> jobNoteDOs) {
        StringBuilder sql = new StringBuilder();
        for (JobNoteDO jobNoteDO : jobNoteDOs) {
            sql.append(buildJobNote(jobNoteDO));
        }
        return sql.toString();
    }

    public static String buildJobEntryDatabase(List<JobentryDatabaseDO> jobentryDatabaseDOs) {
        StringBuilder sql = new StringBuilder();
        for (JobentryDatabaseDO jobentryDatabaseDOb : jobentryDatabaseDOs) {
            sql.append(buildJobEntryDatabase(jobentryDatabaseDOb));
        }
        return sql.toString();
    }

    public static String buildEtlSyncCheckConfig(EtlSyncCheckConfigDO checkConfigDO) {
        return INSERT_ETL_SYNC_CHECK_CONFIG_PREFIX + VALUES_PREFIX + checkConfigDO.getSourceServiceModule() +
                COMMA + ifAddSingleQuote(checkConfigDO.getSourceSchemaName()) +
                COMMA + ifAddSingleQuote(checkConfigDO.getSourceTableName()) +
                COMMA + ifAddSingleQuote(checkConfigDO.getSourceJoinCondition()) +
                COMMA + checkConfigDO.getSourceDeleteType() +
                COMMA + ifAddSingleQuote(checkConfigDO.getSourceWhereCondition()) +
                COMMA + checkConfigDO.getSourceTableType() +
                COMMA + checkConfigDO.getTargetServiceModule() +
                COMMA + ifAddSingleQuote(checkConfigDO.getTargetSchemaName()) +
                COMMA + ifAddSingleQuote(checkConfigDO.getTargetTableName()) +
                COMMA + ifAddSingleQuote(checkConfigDO.getTargetJoinCondition()) +
                COMMA + checkConfigDO.getTargetDeleteType() +
                COMMA + ifAddSingleQuote(checkConfigDO.getTargetWhereCondition()) +
                COMMA + checkConfigDO.getTargetTableType() + VALUES_SUFFIX + LINE_FEED;
    }

    private static String ifAddSingleQuote(String s) {
        if (Objects.nonNull(s)) {
            return SINGLE_QUOTE + s + SINGLE_QUOTE;
        }
        return null;
    }

    public static String buildSwitchDatasource(StepAttributeDO stepAttributeDO, Long newDatasourceId) {
        return UPDATE_STEP_ATTRIBUTE_PREFIX + newDatasourceId + UPDATE_STEP_ATTRIBUTE_SUFFIX + stepAttributeDO.getIdStepAttribute()
                + SEMICOLON + LINE_FEED;
    }

    public static String buildDmlSubscribeConfig(DmlSubscribeConfigDO dmlSubscribeConfigDO) {
        return INSERT_DML_SUBSCRIBE_CONFIG_PREFIX + VALUES_PREFIX + dmlSubscribeConfigDO.getTargetServiceModule() +
                COMMA + ifAddSingleQuote(dmlSubscribeConfigDO.getSourceTableTopic()) +
                COMMA + ifAddSingleQuote(dmlSubscribeConfigDO.getTargetSchemaName()) +
                COMMA + ifAddSingleQuote(dmlSubscribeConfigDO.getTargetTableName()) +
                COMMA + dmlSubscribeConfigDO.getOperationType() +
                COMMA + dmlSubscribeConfigDO.getEnableStatus() +
                COMMA + dmlSubscribeConfigDO.getDmlConsumeType() +
                COMMA + ifAddSingleQuote(dmlSubscribeConfigDO.getDmlSourceShardingField()) + VALUES_SUFFIX + LINE_FEED;
    }
}

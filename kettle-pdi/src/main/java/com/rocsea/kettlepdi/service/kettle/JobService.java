package com.rocsea.kettlepdi.service.kettle;

import com.rocsea.etlcommon.dao.kettleresource.*;
import com.rocsea.etlcommon.model.bo.kettlepdi.IdMaxJobRelatedBO;
import com.rocsea.etlcommon.model.entity.kettleresource.*;
import com.rocsea.etlcommon.utils.MysqlBuilderUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
@Service
public class JobService {
    private final JobDAO jobDAO;
    private final JobAttributeDAO jobAttributeDAO;
    private final JobEntryDAO jobEntryDAO;
    private final JobEntryAttributeDAO jobEntryAttributeDAO;
    private final JobHopDAO jobHopDAO;
    private final JobEntryCopyDAO jobEntryCopyDAO;
    private final JobEntryDatabaseDAO jobEntryDatabaseDAO;
    private final JobNoteDAO jobNoteDAO;

    public JobService(JobDAO jobDAO,
                      JobAttributeDAO jobAttributeDAO,
                      JobEntryDAO jobEntryDAO,
                      JobEntryAttributeDAO jobEntryAttributeDAO,
                      JobHopDAO jobHopDAO,
                      JobEntryCopyDAO jobEntryCopyDAO,
                      JobEntryDatabaseDAO jobEntryDatabaseDAO,
                      JobNoteDAO jobNoteDAO) {
        this.jobDAO = jobDAO;
        this.jobAttributeDAO = jobAttributeDAO;
        this.jobEntryDAO = jobEntryDAO;
        this.jobEntryAttributeDAO = jobEntryAttributeDAO;
        this.jobHopDAO = jobHopDAO;
        this.jobEntryCopyDAO = jobEntryCopyDAO;
        this.jobEntryDatabaseDAO = jobEntryDatabaseDAO;
        this.jobNoteDAO = jobNoteDAO;

    }

    public IdMaxJobRelatedBO getidMaxJobRelated() {
        //查 r_job ID最大值
        Optional<Long> maxJobOptional = jobDAO.queryIdJobMax();
        Long jobMaxId = maxJobOptional.orElse(0L);

        // 查 r_job_attribute ID最大值
        Optional<Long> maxJobAttributeOptional = jobAttributeDAO.queryIdJobAttributeMax();
        Long jobAttributeMaxId = maxJobAttributeOptional.orElse(0L);

        // 查 r_jobentry ID最大值
        Optional<Long> maxJobEntryOptional = jobEntryDAO.queryTargetIdJobEntryMax();
        Long jobEntryMaxId = maxJobEntryOptional.orElse(0L);

        // 查 r_jobentry_attribute ID 最大值
        Optional<Long> maxJobEntryAttributeOptional = jobEntryAttributeDAO.queryTargetJobEntryAttributeMax();
        Long jobEntryAttributeMaxId = maxJobEntryAttributeOptional.orElse(0L);

        // 查 r_jobentry_copy ID 最大值
        Optional<Long> maxJobEntryCopyOptional = jobEntryCopyDAO.queryTargetJobEntryCopyMax();
        Long jobEntryCopyMaxId = maxJobEntryCopyOptional.orElse(0L);

        // 查 r_job_hop ID 最大值
        Optional<Long> maxJobHopOptional = jobHopDAO.queryTargetJobHopMax();
        Long jobHopMaxId = maxJobHopOptional.orElse(0L);

        return new IdMaxJobRelatedBO(jobMaxId, jobAttributeMaxId,
                jobEntryMaxId, jobEntryAttributeMaxId, jobEntryCopyMaxId, jobHopMaxId);
    }

    public List<JobDO> listJob(Long idDirectory) {
        return jobDAO.queryByIdDirectory(idDirectory);
    }

    public List<JobAttributeDO> listJobAttribute(Long idJob) {
        return jobAttributeDAO.queryListByIdJob(idJob);
    }

    public List<JobentryCopyDO> listJobEntryCopy(Long idJobEntry) {
        return jobEntryCopyDAO.queryListByIdJobEntry(idJobEntry);
    }

    public List<JobentryDatabaseDO> listJobEntryDatabase(Long idJobEntry) {
        return jobEntryDatabaseDAO.queryListByIdJobEntry(idJobEntry);
    }

    public List<JobHopDO> listJobHop(Long idJob) {
        return jobHopDAO.queryListByIdJob(idJob);
    }

    public List<JobNoteDO> listJobNote(Long idJob) {
        return jobNoteDAO.queryListByIdJob(idJob);
    }

    public List<JobentryDO> listJobEntry(Long idJob) {
        return jobEntryDAO.queryListByIdJob(idJob);
    }

    public List<JobentryCopyDO> queryJobEntryCopy(Long idJob) {
        return jobEntryCopyDAO.queryListByIdJob(idJob);
    }

    public List<JobentryAttributeDO> listJobEntryAttribute(Long idJobentry) {
        return jobEntryAttributeDAO.queryListByIdJobEntry(idJobentry);
    }

    public void export(Long idDirectory, StringBuilder sql) {
        List<JobDO> jobDOs = jobDAO.queryByIdDirectory(idDirectory);
        for (JobDO jobDO : jobDOs) {
            final Long idJob = jobDO.getIdJob();
            List<JobAttributeDO> jobAttributeDOs = jobAttributeDAO.queryListByIdJob(idJob);
            List<JobHopDO> jobHopDOs = jobHopDAO.queryListByIdJob(idJob);
            List<JobentryDO> jobentryDOs = jobEntryDAO.queryListByIdJob(idJob);
            List<JobentryAttributeDO> jobentryAttributeDOs = jobEntryAttributeDAO.queryListByIdJob(idJob);
            List<JobentryCopyDO> jobentryCopyDOs = jobEntryCopyDAO.queryListByIdJob(idJob);
            List<JobNoteDO> jobNoteDOs = jobNoteDAO.queryListByIdJob(idJob);
            List<JobentryDatabaseDO> jobentryDatabaseDOs = jobEntryDatabaseDAO.queryListByIdJob(idJob);
            sql.append(MysqlBuilderUtils.buildJob(jobDO));
            sql.append(MysqlBuilderUtils.buildJobAttribute(jobAttributeDOs));
            sql.append(MysqlBuilderUtils.buildJobHop(jobHopDOs));
            sql.append(MysqlBuilderUtils.buildJobEntry(jobentryDOs));
            sql.append(MysqlBuilderUtils.buildJobEntryAttribute(jobentryAttributeDOs));
            sql.append(MysqlBuilderUtils.buildJobEntryCopy(jobentryCopyDOs));
            sql.append(MysqlBuilderUtils.buildJobNote(jobNoteDOs));
            sql.append(MysqlBuilderUtils.buildJobEntryDatabase(jobentryDatabaseDOs));
        }
    }
}

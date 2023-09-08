package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobEntryCopyMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobentryCopyDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_job_entry_copy
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobEntryCopyDAO {
    @Resource
    private JobEntryCopyMapper jobEntryCopyMapper;

    /**
     * 查询发布环境：r_jobentry_copy 表
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryTargetJobEntryCopyMax() {
        DynamicQuery<JobentryCopyDO> dynamicQuery = DynamicQuery.createQuery(JobentryCopyDO.class);
        return jobEntryCopyMapper.selectMaxByDynamicQuery(JobentryCopyDO::getIdJobentryCopy, dynamicQuery);
    }

    /**
     * 查询中间库：根据 r_jobentry 主键ID，查询 r_jobentry_copy
     *
     * @param idJobEntry r_jobentry 主键ID
     * @return r_jobentry_copy 实体集合
     */
    public List<JobentryCopyDO> queryListByIdJobEntry(Long idJobEntry) {
        DynamicQuery<JobentryCopyDO> dynamicQuery = DynamicQuery.createQuery(JobentryCopyDO.class)
                .and(JobentryCopyDO::getIdJobentry, isEqual(idJobEntry));
        return jobEntryCopyMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询中间库：根据 r_job 主键ID，查询 r_jobentry_copy
     * @param idJob r_job 主键ID
     * @return r_jobentry_attribute 实体集合
     */
    public List<JobentryCopyDO> queryListByIdJob(Long idJob) {
        DynamicQuery<JobentryCopyDO> dynamicQuery = DynamicQuery.createQuery(JobentryCopyDO.class)
                .and(JobentryCopyDO::getIdJob, isEqual(idJob));
        return jobEntryCopyMapper.selectByDynamicQuery(dynamicQuery);
    }
}

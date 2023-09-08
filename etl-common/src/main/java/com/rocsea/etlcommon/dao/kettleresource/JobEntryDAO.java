package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobEntryMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobentryDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_job_entry
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobEntryDAO {
    @Resource
    private JobEntryMapper jobEntryMapper;

    /**
     * 查询发布环境：r_job_entry 表
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryTargetIdJobEntryMax() {
        DynamicQuery<JobentryDO> dynamicQuery = DynamicQuery.createQuery(JobentryDO.class);
        return jobEntryMapper.selectMaxByDynamicQuery(JobentryDO::getIdJobentry, dynamicQuery);
    }

    /**
     * 查询中间库：根据 r_job 主键ID，查询 r_jobentry
     *
     * @param idJob r_job 主键ID
     * @return r_jobentry 实体集合
     */
    public List<JobentryDO> queryListByIdJob(Long idJob) {
        DynamicQuery<JobentryDO> dynamicQuery = DynamicQuery.createQuery(JobentryDO.class)
                .and(JobentryDO::getIdJob, isEqual(idJob));
        return jobEntryMapper.selectByDynamicQuery(dynamicQuery);
    }
}

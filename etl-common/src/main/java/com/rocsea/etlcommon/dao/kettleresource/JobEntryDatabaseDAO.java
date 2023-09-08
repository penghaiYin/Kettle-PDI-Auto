package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobEntryDatabaseMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobentryDatabaseDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_jobentry_database
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobEntryDatabaseDAO {
    @Resource
    private JobEntryDatabaseMapper jobEntryDatabaseMapper;

    /**
     * 查询中间库：根据 r_jobentry 主键ID，查询 r_jobentry_database
     *
     * @param idJobEntry r_jobentry 主键ID
     * @return r_jobentry_database 实体集合
     */
    public List<JobentryDatabaseDO> queryListByIdJobEntry(Long idJobEntry) {
        DynamicQuery<JobentryDatabaseDO> dynamicQuery = DynamicQuery.createQuery(JobentryDatabaseDO.class)
                .and(JobentryDatabaseDO::getIdJobentry, isEqual(idJobEntry));
        return jobEntryDatabaseMapper.selectByDynamicQuery(dynamicQuery);
    }

    public List<JobentryDatabaseDO> queryListByIdJob(Long idJob) {
        DynamicQuery<JobentryDatabaseDO> dynamicQuery = DynamicQuery.createQuery(JobentryDatabaseDO.class)
                .and(JobentryDatabaseDO::getIdJob, isEqual(idJob.intValue()));
        return jobEntryDatabaseMapper.selectByDynamicQuery(dynamicQuery);
    }
}

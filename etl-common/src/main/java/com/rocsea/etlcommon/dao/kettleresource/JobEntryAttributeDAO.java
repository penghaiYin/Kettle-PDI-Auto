package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobEntryAttributeMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobentryAttributeDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_jobentry_attribute
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobEntryAttributeDAO {

    @Resource
    private JobEntryAttributeMapper jobEntryAttributeMapper;

    /**
     * 查询发布环境：r_jobentry_attributer 表
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryTargetJobEntryAttributeMax() {
        DynamicQuery<JobentryAttributeDO> dynamicQuery = DynamicQuery.createQuery(JobentryAttributeDO.class);
        return jobEntryAttributeMapper.selectMaxByDynamicQuery(JobentryAttributeDO::getIdJobentryAttribute, dynamicQuery);
    }

    /**
     * 查询中间库：根据 r_jobentry 主键ID，查询 r_jobentry_attribute
     *
     * @param idJobEntry r_jobentry 主键ID
     * @return r_jobentry_attribute 实体集合
     */
    public List<JobentryAttributeDO> queryListByIdJobEntry(Long idJobEntry) {
        DynamicQuery<JobentryAttributeDO> dynamicQuery = DynamicQuery.createQuery(JobentryAttributeDO.class)
                .and(JobentryAttributeDO::getIdJobentry, isEqual(idJobEntry));
        return jobEntryAttributeMapper.selectByDynamicQuery(dynamicQuery);
    }

    public List<JobentryAttributeDO> queryListByIdJob(Long idJob) {
        DynamicQuery<JobentryAttributeDO> dynamicQuery = DynamicQuery.createQuery(JobentryAttributeDO.class)
                .and(JobentryAttributeDO::getIdJob, isEqual(idJob));
        return jobEntryAttributeMapper.selectByDynamicQuery(dynamicQuery);
    }
}

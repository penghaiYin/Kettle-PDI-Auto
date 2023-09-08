package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobAttributeMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobAttributeDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_job_attribute
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobAttributeDAO {
    @Resource
    private JobAttributeMapper jobAttributeMapper;

    /**
     * 查询发布环境：r_job_attribute 表
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryIdJobAttributeMax() {
        DynamicQuery<JobAttributeDO> dynamicQuery = DynamicQuery.createQuery(JobAttributeDO.class);
        return jobAttributeMapper.selectMaxByDynamicQuery(JobAttributeDO::getIdJobAttribute, dynamicQuery);
    }

    /**
     * 查询中间库：根据 r_job 主键ID，查询 r_job_attribute
     *
     * @param idJob r_job 主键ID
     * @return r_job_attribute 实体集合
     */
    public List<JobAttributeDO> queryListByIdJob(Long idJob) {
        DynamicQuery<JobAttributeDO> dynamicQuery = DynamicQuery.createQuery(JobAttributeDO.class)
                .and(JobAttributeDO::getIdJob, isEqual(idJob));
        return jobAttributeMapper.selectByDynamicQuery(dynamicQuery);
    }
}

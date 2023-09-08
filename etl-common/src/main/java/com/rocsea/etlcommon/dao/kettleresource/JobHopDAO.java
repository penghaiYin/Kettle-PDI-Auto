package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobHopMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobHopDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_job_hop
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobHopDAO {
    @Resource
    private JobHopMapper jobHopMapper;

    /**
     * 查询发布环境：r_job_hop 表
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryTargetJobHopMax() {
        DynamicQuery<JobHopDO> dynamicQuery = DynamicQuery.createQuery(JobHopDO.class);
        return jobHopMapper.selectMaxByDynamicQuery(JobHopDO::getIdJobHop, dynamicQuery);
    }

    /**
     * 查询中间库：根据 r_job 主键ID，查询 r_job_hop
     *
     * @param idJob r_job 主键ID
     * @return r_job_hop 实体集合
     */
    public List<JobHopDO> queryListByIdJob(Long idJob) {
        DynamicQuery<JobHopDO> dynamicQuery = DynamicQuery.createQuery(JobHopDO.class)
                .and(JobHopDO::getIdJob, isEqual(idJob));
        return jobHopMapper.selectByDynamicQuery(dynamicQuery);
    }
}

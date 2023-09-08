package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobNoteMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobNoteDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_job
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobNoteDAO {
    @Resource
    private JobNoteMapper jobNoteMapper;

    /**
     * 查询中间库：根据 r_job 主键ID，查询 r_job_hop
     *
     * @param idJob r_job 主键ID
     * @return r_job_hop 实体集合
     */
    public List<JobNoteDO> queryListByIdJob(Long idJob) {
        DynamicQuery<JobNoteDO> dynamicQuery = DynamicQuery.createQuery(JobNoteDO.class)
                .and(JobNoteDO::getIdJob, isEqual(idJob));
        return jobNoteMapper.selectByDynamicQuery(dynamicQuery);
    }
}

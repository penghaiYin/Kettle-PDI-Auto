package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.JobMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.JobDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_job
 * @Author RocSea
 * @Date 2022/7/19
 */
@Repository
public class JobDAO {
    @Resource
    private JobMapper jobMapper;

    /**
     * 根据目录ID，查询 r_job 表的集合
     *
     * @param idDirectory 目录ID
     * @return r_job 实体集合
     */
    public List<JobDO> queryByIdDirectory(Long idDirectory) {
        DynamicQuery<JobDO> dynamicQuery = DynamicQuery.createQuery(JobDO.class)
                .and(JobDO::getIdDirectory, isEqual(idDirectory.intValue()));
        return jobMapper.selectByDynamicQuery(dynamicQuery);
    }


    /**
     * 查询发布环境：r_job 表
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryIdJobMax() {
        DynamicQuery<JobDO> dynamicQuery = DynamicQuery.createQuery(JobDO.class);
        return jobMapper.selectMaxByDynamicQuery(JobDO::getIdJob, dynamicQuery);
    }
}

package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.StepDatabaseMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.StepDatabaseDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_step_database 表
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class StepDatabaseDAO {
    @Resource
    private StepDatabaseMapper stepDatabaseMapper;

    /**
     * 查询中间库：根据r_transformation 主键ID，查询 r_step_database
     *
     * @param idTransformation r_transformation 主键ID
     * @return r_step_database 实体集合
     */
    public List<StepDatabaseDO> queryListByIdTransformation(Long idTransformation) {
        DynamicQuery<StepDatabaseDO> dynamicQuery = DynamicQuery.createQuery(StepDatabaseDO.class)
                .and(StepDatabaseDO::getIdTransformation, isEqual(idTransformation));
        return stepDatabaseMapper.selectByDynamicQuery(dynamicQuery);
    }
}

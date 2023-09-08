package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.StepMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.StepDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.in;
import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_step
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class StepDAO {
    @Resource
    private StepMapper stepMapper;

    /**
     * 根据r_transformation主键ID，查询 r_step
     *
     * @return r_step 实体集合
     */
    public List<StepDO> queryListByIdTransformation(Long idTransformation) {
        DynamicQuery<StepDO> dynamicQuery = DynamicQuery.createQuery(StepDO.class)
                .and(StepDO::getIdTransformation, isEqual(idTransformation));
        return stepMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询集合
     * @param idTransformation 转换ID
     * @param names 名字集合
     * @return StepDO集合
     */
    public List<StepDO> getList(Long idTransformation, List<String> names) {
        DynamicQuery<StepDO> dynamicQuery = DynamicQuery.createQuery(StepDO.class)
                .and(StepDO::getIdTransformation, isEqual(idTransformation))
                .and(StepDO::getName, in(names));
        return stepMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询 r_step
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryIdStepMax() {
        DynamicQuery<StepDO> queryStepMaxId = DynamicQuery.createQuery(StepDO.class);
        return stepMapper.selectMaxByDynamicQuery(StepDO::getIdStep, queryStepMaxId);
    }
}

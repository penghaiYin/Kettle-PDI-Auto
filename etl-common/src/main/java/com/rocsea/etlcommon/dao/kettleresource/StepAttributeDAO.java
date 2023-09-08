package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.StepAttributeMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.StepAttributeDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_step_attribute
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class StepAttributeDAO {
    @Resource
    private StepAttributeMapper stepAttributeMapper;

    /**
     * 根据r_step的主键，查询 r_step_attribute
     *
     * @param idStep r_step的主键
     * @return r_step_attribute 实体集合
     */
    public List<StepAttributeDO> queryListByIdStep(Long idStep) {
        DynamicQuery<StepAttributeDO> dynamicQuery = DynamicQuery.createQuery(StepAttributeDO.class)
                .and(StepAttributeDO::getIdStep, isEqual(idStep));
        return stepAttributeMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询返回一个StepAttributeDO
     * @param idStep 步骤ID
     * @param code code
     * @return Optional<StepAttributeDO>
     */
    public Optional<StepAttributeDO> getOne(Long idStep, String code) {
        DynamicQuery<StepAttributeDO> dynamicQuery = DynamicQuery.createQuery(StepAttributeDO.class)
                .and(StepAttributeDO::getIdStep, isEqual(idStep))
                .and(StepAttributeDO::getCode, isEqual(code));
        return stepAttributeMapper.selectFirstByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询 r_step_attribute
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryIdStepAttributeMax() {
        DynamicQuery<StepAttributeDO> queryMaxId = DynamicQuery.createQuery(StepAttributeDO.class);
        return stepAttributeMapper.selectMaxByDynamicQuery(StepAttributeDO::getIdStepAttribute, queryMaxId);
    }

    public List<StepAttributeDO> queryListByIdTransformation(Long idTransformation) {
        DynamicQuery<StepAttributeDO> dynamicQuery = DynamicQuery.createQuery(StepAttributeDO.class)
                .and(StepAttributeDO::getIdTransformation, isEqual(idTransformation));
        return stepAttributeMapper.selectByDynamicQuery(dynamicQuery);
    }
}

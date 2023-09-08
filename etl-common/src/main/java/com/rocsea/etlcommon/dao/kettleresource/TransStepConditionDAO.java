package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.TransStepConditionMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.TransStepConditionDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_trans_step_condition 表
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class TransStepConditionDAO {
    @Resource
    private TransStepConditionMapper transStepConditionMapper;

    /**
     * 查询中间库：r_trans_step_condition
     *
     * @param idTransformation r_transformation的主键ID
     * @return r_trans_step_condition 实体集合
     */
    public List<TransStepConditionDO> queryListByIdTransformation(Long idTransformation) {
        DynamicQuery<TransStepConditionDO> dynamicQuery = DynamicQuery.createQuery(TransStepConditionDO.class)
                .and(TransStepConditionDO::getIdTransformation, isEqual(idTransformation));
        return transStepConditionMapper.selectByDynamicQuery(dynamicQuery);
    }
}

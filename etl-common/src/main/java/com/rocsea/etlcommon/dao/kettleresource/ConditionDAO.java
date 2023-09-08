package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.ConditionMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.ConditionDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.in;

/**
 * 查询 r_condition 表
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class ConditionDAO {
    @Resource
    private ConditionMapper conditionMapper;

    /**
     * 根据主键ID，查询 r_condition 表
     *
     * @param idConditionList 主键ID集合
     * @return 实体集合
     */
    public List<ConditionDO> queryListByIds(List<Long> idConditionList) {
        DynamicQuery<ConditionDO> dynamicQuery = DynamicQuery.createQuery(ConditionDO.class)
                        .and(ConditionDO::getIdCondition, in(idConditionList));
        return conditionMapper.selectByDynamicQuery(dynamicQuery);
    }
}

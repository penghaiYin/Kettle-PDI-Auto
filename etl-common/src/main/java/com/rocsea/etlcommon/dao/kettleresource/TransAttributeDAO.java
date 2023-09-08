package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.TransAttributeMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.TransAttributeDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_trans_attribute 表
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class TransAttributeDAO {
    @Resource
    private TransAttributeMapper transAttributeMapper;

    /**
     * 根据 r_transformation 主键ID，查询 r_trans_attribute
     *
     * @param idTransformation r_transformation 主键ID
     * @return r_trans_attribute实体集合
     */
    public List<TransAttributeDO> queryListByIdTransformation(Long idTransformation) {
        DynamicQuery<TransAttributeDO> dynamicQuery = DynamicQuery.createQuery(TransAttributeDO.class)
                .and(TransAttributeDO::getIdTransformation, isEqual(idTransformation));
        return transAttributeMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询r_trans_attribute
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryIdTransAttributeMax() {
        DynamicQuery<TransAttributeDO> queryMaxId = DynamicQuery.createQuery(TransAttributeDO.class);
        return transAttributeMapper.selectMaxByDynamicQuery(TransAttributeDO::getIdTransAttribute, queryMaxId);
    }
}

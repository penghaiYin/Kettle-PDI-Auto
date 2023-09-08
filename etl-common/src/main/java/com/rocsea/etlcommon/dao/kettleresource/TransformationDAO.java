package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.TransformationMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.TransformationDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.in;

/**
 * 查询 r_transformation 表
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class TransformationDAO {
    @Resource
    private TransformationMapper transformationMapper;

    /**
     * 根据主键ID集合，查询 r_transformation 表的集合
     *
     * @param transformationIdList 主键ID集合
     * @return r_transformation实体集合
     */
    public List<TransformationDO> queryListByIds(List<Long> transformationIdList) {
        DynamicQuery<TransformationDO> dynamicQuery = DynamicQuery.createQuery(TransformationDO.class)
                .and(TransformationDO::getIdTransformation, in(transformationIdList));
        return transformationMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 根据目录ID，查询 r_transformation
     *
     * @param idDirectory 目录ID
     * @return r_transformation实体集合
     */
    public List<TransformationDO> queryByIdDirectory(Long idDirectory) {
        DynamicQuery<TransformationDO> dynamicQuery = DynamicQuery.createQuery(TransformationDO.class)
                .and(TransformationDO::getIdDirectory, in(idDirectory));
        return transformationMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询 r_transformation 表
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryIdTransformationMax() {
        DynamicQuery<TransformationDO> dynamicQuery = DynamicQuery.createQuery(TransformationDO.class);
        return transformationMapper.selectMaxByDynamicQuery(TransformationDO::getIdTransformation, dynamicQuery);
    }

}

package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.TransHopMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.TransHopDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_trans_hop
 *
 * @Author RocSea
 * @Date 2022/7/13
 */
@Repository
public class TransHopDAO {
    @Resource
    private TransHopMapper transHopMapper;

    /**
     * 查询中间库：r_trans_hop
     *
     * @param idTransformation r_transformation 主键ID
     * @return r_trans_hop 实体集合
     */
    public List<TransHopDO> queryListByIdTransformation(Long idTransformation) {
        DynamicQuery<TransHopDO> dynamicQuery = DynamicQuery.createQuery(TransHopDO.class)
                .and(TransHopDO::getIdTransformation, isEqual(idTransformation));
        return transHopMapper.selectByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询 r_trans_hop
     *
     * @return 主键ID最大值
     */
    public Optional<Long> queryIdTransHopMax() {
        DynamicQuery<TransHopDO> queryMaxId = DynamicQuery.createQuery(TransHopDO.class);
        return transHopMapper.selectMaxByDynamicQuery(TransHopDO::getIdTransHop, queryMaxId);
    }
}

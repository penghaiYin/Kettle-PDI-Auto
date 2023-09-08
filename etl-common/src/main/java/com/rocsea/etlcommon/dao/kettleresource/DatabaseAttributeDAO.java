package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.DatabaseAttributeMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.DatabaseAttributeDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 查询 r_database_attribute
 * @Author RocSea
 * @Date 2022/7/14
 */
@Repository
public class DatabaseAttributeDAO {
    @Resource
    private DatabaseAttributeMapper databaseAttributeMapper;


    public Optional<Long> getMaxId(){
        DynamicQuery<DatabaseAttributeDO> dynamicQuery = DynamicQuery.createQuery(DatabaseAttributeDO.class);
        return databaseAttributeMapper.selectMaxByDynamicQuery(DatabaseAttributeDO::getIdDatabaseAttribute, dynamicQuery);
    }
}

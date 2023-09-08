package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.DatabaseMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.DatabaseDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_database
 * @Author RocSea
 * @Date 2022/7/14
 */
@Repository
public class DatabaseDAO {
    @Resource
    private DatabaseMapper databaseMapper;

    /**
     * 查询中间库：r_database 全量配置
     * @return r_database 实体集合
     */
    public Optional<DatabaseDO> getOne(String name, String dataBaseName){
        DynamicQuery<DatabaseDO> dynamicQuery = DynamicQuery.createQuery(DatabaseDO.class)
                .and(DatabaseDO::getDatabaseName, isEqual(dataBaseName))
                .and(DatabaseDO::getName, isEqual(name));
        return databaseMapper.selectFirstByDynamicQuery(dynamicQuery);
    }

    public Optional<Long> getMaxId(){
        DynamicQuery<DatabaseDO> dynamicQuery = DynamicQuery.createQuery(DatabaseDO.class);
        return databaseMapper.selectMaxByDynamicQuery(DatabaseDO::getIdDatabase, dynamicQuery);
    }

    /**
     * 查询中间库：r_database 全量配置
     * @return r_database 实体集合
     */
    public List<DatabaseDO> queryAll(){
        return databaseMapper.selectAll();
    }
}

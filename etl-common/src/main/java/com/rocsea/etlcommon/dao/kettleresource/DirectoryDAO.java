package com.rocsea.etlcommon.dao.kettleresource;

import com.github.wz2cool.dynamic.DynamicQuery;
import com.rocsea.etlcommon.mapper.kettleresource.DirectoryMapper;
import com.rocsea.etlcommon.model.entity.kettleresource.DirectoryDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.in;
import static com.github.wz2cool.dynamic.builder.DynamicQueryBuilderHelper.isEqual;

/**
 * 查询 r_directory
 * @Author RocSea
 * @Date 2022/7/14
 */
@Repository
public class DirectoryDAO {
    @Resource
    private DirectoryMapper directoryMapper;

    /**
     * 查询返回一个目录
     * @param parentId 父目录ID
     * @param name 名字
     * @return Optional<DirectoryDO>
     */
    public Optional<DirectoryDO> getOne(Integer parentId, String name){
        DynamicQuery<DirectoryDO> dynamicQuery = DynamicQuery.createQuery(DirectoryDO.class)
                .and(DirectoryDO::getIdDirectoryParent, isEqual(parentId))
                .and(DirectoryDO::getDirectoryName, isEqual(name));
        return directoryMapper.selectFirstByDynamicQuery(dynamicQuery);
    }

    /**
     * 查询目录集合
     * @param parentId 父目录ID
     * @param names 多个名字
     * @return Optional<DirectoryDO>
     */
    public List<DirectoryDO> getList(Integer parentId, List<String> names){
        DynamicQuery<DirectoryDO> dynamicQuery = DynamicQuery.createQuery(DirectoryDO.class)
                .and(DirectoryDO::getIdDirectoryParent, isEqual(parentId))
                .and(DirectoryDO::getDirectoryName, in(names));
        return directoryMapper.selectByDynamicQuery(dynamicQuery);
    }



    public Optional<Long> getMaxId(){
        DynamicQuery<DirectoryDO> dynamicQuery = DynamicQuery.createQuery(DirectoryDO.class);
        return directoryMapper.selectMaxByDynamicQuery(DirectoryDO::getIdDirectory, dynamicQuery);
    }

    public void insert(DirectoryDO directoryDO){
        directoryMapper.insert(directoryDO);
    }

    public List<DirectoryDO> getList(Integer parentId) {
        DynamicQuery<DirectoryDO> dynamicQuery = DynamicQuery.createQuery(DirectoryDO.class)
                .and(DirectoryDO::getIdDirectoryParent, isEqual(parentId));
        return directoryMapper.selectByDynamicQuery(dynamicQuery);
    }
}
